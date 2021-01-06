//Jacinthe Beaudry (40126080)
//COMP249
//Assignment #3 
//November 13th 2020



package pack1;

/** WHAT DOES THIS CLASS DO?
 * 
 * This class creates Article objects.
 * 
 * @author Jacinthe Beaudry (40126080)
 * @version 1.0
 * 
 */

public class Article {

	private String id;
	private String author;
	private String journal;
	private String title;
	private String year;
	private String volume;
	private String number;
	private String pages;
	private String keywords;
	private String doi;
	private String issn;
	private String month;
	
	/** 
	 * This main method creates Article objects.
	 * 
	 * @param id  String that indicates id
	 * @param price  String that indicates the author
	 * @param journal String that indicates the journal
	 * @param title String that indicates the title
	 * @param year String that indicates the title
	 * @param volume String that indicates the volume
	 * @param number String that indicates the number
	 * @param pages String that indicates the pages
	 * @param keywords String that indicates the keywords
	 * @param doi String that indicates the doi
	 * @param issn String that indicates the ISSN
	 * @param month String that indicates the month
	 * 
	 */
	
	public Article() {
		this.id=null;
		this.author=null;
		this.journal=null;
		this.title=null;
		this.year=null;
		this.volume=null;
		this.number=null;
		this.pages=null;
		this.keywords=null;
		this.doi=null;
		this.issn=null;
		this.month=null;
	}
	
	public Article (String id, String author, String journal, String title, String year, String volume, String number, 
					String pages, String keywords, String doi, String issn, String month) {
		this.id=id;
		this.author=author;
		this.journal=journal;
		this.title=title;
		this.year=year;
		this.volume=volume;
		this.number=number;
		this.pages=pages;
		this.keywords=keywords;
		this.doi=doi;
		this.issn=issn;
		this.month=month;
	}
	
	public Article(Article a) {
		this.id=a.getId();
		this.author=a.getAuthor();
		this.journal=a.getJournal();
		this.title=a.getTitle();
		this.year=a.getYear();
		this.volume=a.getVolume();
		this.number=a.getNumber();
		this.pages=a.getPages();
		this.keywords=a.getKeywords();
		this.doi=a.getDoi();
		this.issn=a.getIssn();
		this.month=a.getMonth();
	}
	
	public Article clone() {
		return new Article(this);
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getJournal() {
		return journal;
	}
	
	public void setJournal(String journal) {
		this.journal = journal;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getVolume() {
		return volume;
	}
	
	public void setVolume(String vol) {
		volume = vol;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getPages() {
		return pages;
	}
	
	public void setPages(String page) {
		pages = page;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String kw) {
		keywords = kw;
	}
	
	public String getDoi() {
		return doi;
	}
	
	public void setDoi(String doi) {
		this.doi = doi;
	}
	
	public String getIssn() {
		return issn;
	}
	
	public void setIssn(String issn) {
		this.issn = issn;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	


}
