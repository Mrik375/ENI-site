package fr.eni.site.bo;

public enum ArticleStatus {
	PAS_COMMENCEE(0),
	EN_COURS(1),
	CLOTUREE(2),
	LIVREE(3),
	ANNULEE(100);

	private final int code;

	ArticleStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ArticleStatus fromCode(int code) {
		for (ArticleStatus status : values()) {
			if (status.code == code) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid status code: " + code);
	}
}

