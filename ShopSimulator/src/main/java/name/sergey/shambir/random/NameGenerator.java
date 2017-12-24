package name.sergey.shambir.random;

import java.util.Random;

public class NameGenerator {
    private static final String[] NAMES = {
        "Aaliyah",  "Aaron",     "Aarushi",   "Abagail",   "Abbey",    "Abbi",     "Abbie",     "Abby",
        "Abdul",    "Abdullah",  "Abe",       "Abel",      "Abi",      "Abia",     "Angie",     "Angus",
        "Anika",    "Anisa",     "Anita",     "Aniya",     "Aniyah",   "Annalisa", "Annalise",  "Anne",
        "Anneke",   "Annemarie", "Annette",   "Annie",     "Cayden",   "Caydon",   "Cayla",     "Cece",
        "Cecelia",  "Cecil",     "Cecilia",   "Conan",     "Conner",   "Connie",   "Connor",    "Conor",
        "Conrad",   "Constance", "Ellington", "Elliot",    "Elliott",  "Ellis",    "Ellis",     "Elly",
        "Elmer",    "Elmo",      "Elodie",    "Eloise",    "Elora",    "Elsa",     "Elsie",     "Elspeth",
        "Emerson",  "Emerson",   "Emery",     "Emet",      "Emil",     "Emilee",   "Emilia",    "Enzo",
        "Eoghan",   "Eoin",      "Eric",      "Erica",     "Erick",    "Erik",     "Erika",     "Erin",
        "Eris",     "Ernest",    "Ernesto",   "Ernie",     "Errol",    "Evie",     "Evita",     "Ewan",
        "Ezekiel",  "Ezio",      "Ezra",      "Fabian",    "Fleur",    "Flick",    "Flo",       "Flora",
        "Florence", "Floyd",     "Flynn",     "Ford",      "Forest",   "Forrest",  "Foster",    "Fox",
        "Fran",     "Frances",   "Francesca", "Francesco", "Francine", "Francis",  "Francisco", "Frank",
        "Frankie",  "Gibson",    "Gideon",    "Gigi",      "Gil",      "Gilbert",  "Gilberto",  "Giles",
        "Gillian",  "Gina",      "Ginger",    "Ginny",     "Gino",     "Giorgio",  "Giovanna",  "Greg",
        "Gregg",    "Gregor",    "Gregory",   "Greta",     "Gretchen", "Grey",     "Hera",      "Herbert",
        "Herman",   "Hermione",  "Hester",    "Heston",    "Hetty",    "Hilary",   "Hilary",    "Hilda",
        "Hillary",  "Holden",    "Hollie",    "Holly",     "Ignacio",  "Igor",     "Ike",       "Ila",
        "Ilene",    "Iliana",    "Ilona",     "Knox",      "Kobe",     "Koby",     "Kody",      "Kolby",
        "Kora",     "Kori",      "Kourtney",  "Kris",      "Kris",     "Krish",    "Krista",    "Kristen",
        "Kristi",   "Leilani",   "Lela",      "Leland",    "Lena",     "Lennie",   "Lennon",    "Lennox",
        "Lila",     "Lilac",     "Lilah",     "Lili",      "Lilian",   "Liliana",  "Lilita",    "Lowell",
        "Luann",    "Luca",      "Lucas",     "Lucia",     "Lucian",   "Luciana",  "Luciano",   "Lucie",
        "Lucille",  "Lucinda",   "Lucky",     "Lucy",      "Luigi",    "Phoenix",  "Phyllis",   "Pierce",
        "Piers",    "Pip",       "Piper",     "Pippa",     "Shelley",  "Shelly",   "Shelton",   "Sheri",
        "Sheridan", "Sherlock",  "Sherman",   "Sherri",    "Sherrie",  "Sherry",   "Sheryl",    "Shiloh",
        "Shirley",  "Shivani",   "Timmy",     "Timothy",   "Tina",     "Tisha",    "Tito",      "Titus",
        "Tobias",
    };
    private final EasyRandom random;

    public NameGenerator(EasyRandom random) {
        this.random = random;
    }

    public String nextName() {
        return random.nextItem(NAMES);
    }
}
