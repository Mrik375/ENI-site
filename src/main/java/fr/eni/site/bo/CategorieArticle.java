package fr.eni.site.bo;

public enum CategorieArticle {
	AMEUBLEMENT(1, "Ameublement"),
	INFORMATIQUE(2, "Informatique"),
	SPORT_ET_LOISIR(3, "Sport & Loisir"),
	VETEMENT(4, "Vêtement");

	private final int code;
	private final String description;

	// Constructor
	CategorieArticle(int code, String description) {
		this.code = code;
		this.description = description;
	}

	// Getters
	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}

	public static CategorieArticle fromCode(int code) {
		for (CategorieArticle category : CategorieArticle.values()) {
			if (category.code == code) {
				return category;
			}
		}
		return null;
	}

	public static CategorieArticle fromDescription(String description) {
		for (CategorieArticle category : CategorieArticle.values()) {
			if (category.description.equals(description)) {
				return category;
			}
		}
		return null;
	}
}

