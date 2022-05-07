package com.fang.constants;

public enum SocialSour {
	
//	Facebook("F", "facebookImpl", "FACEBOOK", SystemParameter.getFacebookApplicationId());
	Facebook("F", "facebookImpl", "FACEBOOK", "154276514930548");
//	Google("G", "", "GOOGLE"),
//	Line("L", "", "LINE");
	
	private String sour;
	private String bean;
	private String upperCaseName;
	private String socialappid;
	
	SocialSour(String sour, String bean, String upperCaseName, String socialappid){
		this.sour = sour;
		this.bean = bean;
		this.upperCaseName = upperCaseName;
		this.socialappid = socialappid;
	}
	
	/**
	 * @return 使用者來源(socialsour)
	 */
	public String getSour() { 
		return sour;
	}
	
	/**
	 * @return 不同來源對應的bean名稱
	 */
	public String getBean() {
		return bean;
	}
	
	/**
	 * @return 大寫英文名稱
	 */
	public String getUpperCaseName() {
		return upperCaseName;
	}
	
	/**
	 * @return 應用程式ID
	 */
	public String getSocialappid() {
		return socialappid;
	}
	
	/**
	 * 以sour找到對應的SocialSour
	 */
	public static SocialSour getBySour(String sour) {
		for (SocialSour socialSour : SocialSour.values()) {
			if(socialSour.getSour().equals(sour)) {
				return socialSour;
			}
	    }
	    return null;
	}
}
