package com.literalura.literalura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Principal implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private final Scanner scanner = new Scanner(System.in);

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public void run(String... args) {
        mostrarMenu();
    }

    private void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("""
                === MENÚ LITERALURA ===
                1 - Buscar libro por título
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en determinado año
                5 - Listar libros por idioma
                6 - Mostrar cantidad de libros por idioma
                0 - Salir
                """);
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
                continue;
            }

            switch(opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> mostrarEstadisticasPorIdioma();
            }
        } while(opcion != 0);
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Ingresa el título del libro a buscar: ");
        String titulo = scanner.nextLine();

        try {
            String urlStr = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                System.out.println("Error al conectarse a la API. Código HTTP: " + conn.getResponseCode());
                return;
            }

            Scanner sc = new Scanner(conn.getInputStream());
            StringBuilder inline = new StringBuilder();
            while(sc.hasNext()) inline.append(sc.nextLine());
            sc.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(inline.toString());
            JsonNode results = rootNode.get("results");
            if (!results.isArray() || results.isEmpty()) {
                System.out.println("No se encontraron resultados para ese título.");
                return;
            }

            JsonNode primerLibro = results.get(0);
            String tituloLibro = primerLibro.has("title") ? primerLibro.get("title").asText() : "Desconocido";

            JsonNode autoresNode = primerLibro.get("authors");

            // Variables finales para lambda
            final String[] nombreAutor = { "Desconocido" };
            final Integer[] anioNacimiento = { null };
            final Integer[] anioFallecimiento = { null };

            if (autoresNode != null && autoresNode.isArray() && !autoresNode.isEmpty()) {
                JsonNode primerAutor = autoresNode.get(0);
                nombreAutor[0] = primerAutor.has("name") ? primerAutor.get("name").asText() : "Desconocido";
                if (primerAutor.has("birth_year") && !primerAutor.get("birth_year").isNull())
                    anioNacimiento[0] = primerAutor.get("birth_year").asInt();
                if (primerAutor.has("death_year") && !primerAutor.get("death_year").isNull())
                    anioFallecimiento[0] = primerAutor.get("death_year").asInt();
            }

            JsonNode idiomasNode = primerLibro.get("languages");
            String idioma = "Desconocido";
            if (idiomasNode != null && idiomasNode.isArray() && !idiomasNode.isEmpty())
                idioma = idiomasNode.get(0).asText();

            int descargas = primerLibro.has("downloads") && !primerLibro.get("downloads").isNull()
                    ? primerLibro.get("downloads").asInt()
                    : 0;

            // Guardar o reutilizar autor
            Autor autorEntidad = autorRepository.findAll()
                    .stream()
                    .filter(a -> a.getNombre().equalsIgnoreCase(nombreAutor[0]))
                    .findFirst()
                    .orElseGet(() -> autorRepository.save(new Autor(nombreAutor[0], anioNacimiento[0], anioFallecimiento[0])));

            // Crear libro
            Libro libroEntidad = new Libro(tituloLibro, idioma, descargas, autorEntidad,
                    autorEntidad.getAnioNacimiento(), autorEntidad.getAnioFallecimiento());

            libroRepository.save(libroEntidad);

            System.out.println("Libro guardado correctamente:");
            System.out.println(libroEntidad);

        } catch (Exception e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) System.out.println("No hay libros registrados.");
        else libros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) System.out.println("No hay autores registrados.");
        else autores.forEach(System.out::println);
    }

    // Actualizado para usar derived query del paso 11
    private void listarAutoresVivos() {
        System.out.print("Ingresa el año para filtrar autores vivos: ");
        int anio;
        try {
            anio = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Año inválido.");
            return;
        }

        List<Autor> autores = autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNullOrAnioFallecimientoGreaterThanEqual(anio, anio);

        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en el año " + anio);
        } else {
            System.out.println("=== Autores vivos en el año " + anio + " ===");
            autores.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Ingresa el idioma: ");
        String idioma = scanner.nextLine();
        List<Libro> libros = libroRepository.findAll();
        libros.stream()
                .filter(l -> l.getIdioma().equalsIgnoreCase(idioma))
                .forEach(System.out::println);
    }

    // Paso 10: estadísticas por idioma
    private void mostrarEstadisticasPorIdioma() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        Map<String, Long> librosPorIdioma = libros.stream()
                .collect(Collectors.groupingBy(Libro::getIdioma, Collectors.counting()));

        System.out.println("=== Estadísticas de libros por idioma ===");
        librosPorIdioma.forEach((idioma, cantidad) ->
                System.out.println("Idioma: " + idioma + " → Cantidad de libros: " + cantidad));
    }
}