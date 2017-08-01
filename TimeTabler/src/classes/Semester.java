package classes;

public class Semester {

	public enum SemSeason {
		WINTER, SUMMER
	}; // summer semester comes before winter one

	int year;
	SemSeason season;
	static Semester currentSemester = new Semester(2016, SemSeason.WINTER);

	public Semester(int year, SemSeason season) {
		this.year = year;
		this.season = season;

	}

	public static int howManySemestersAgo(Semester semester) {
		int result = (currentSemester.year - semester.year) * 2;

		if (currentSemester.season == SemSeason.WINTER) {
			result++;
		}
		if (semester.season == SemSeason.WINTER) {
			result--;
		}

		return result;

	}

	public static void setCurrentSemester(Semester semester) {
		currentSemester = semester;

	}

}
