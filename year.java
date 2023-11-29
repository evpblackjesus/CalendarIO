public class year {
    int days = 366;
    int weeks = 52;
    String[] monthName = new String[]{
        "Január",
        "Feburár",
        "Március",
        "Április",
        "Május",
        "Június",
        "Július",
        "Augusztus",
        "Szeptember",
        "Október",
        "November",
        "December"
    };
    int months = monthName.length;

    int[] numofDays = new int[]{
        31,
        29,
        31,
        30,
        31,
        30,
        31,
        31,
        30,
        31,
        30,
        31
    };

    String[] daysNames = new String[]{
        "Hétfő",
        "Kedd",
        "Szerda",
        "Csütörtök",
        "Péntek",
        "Szombat",
        "Vasárnap",
    };

    int[] firstDayOfMonth = new int[]{
        1,
        4,
        5,
        1,
        3,
        6,
        1,
        4,
        7,
        2,
        5,
        7
    };

}
