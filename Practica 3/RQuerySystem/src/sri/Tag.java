package definitivo;

public class Tag {
	private String ID_q;
	private String set_tags;
	
	public Tag(String ID, String tags) {
		setID_q(ID);
		setSet_tags(tags);
	}

	public String getID_q() {
		return ID_q;
	}

	private void setID_q(String iD_q) {
		ID_q = iD_q;
	}

	public String getSet_tags() {
		return set_tags;
	}

	private void setSet_tags(String set_tags) {
		this.set_tags = set_tags;
	}
	

}
