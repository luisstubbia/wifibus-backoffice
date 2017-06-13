package com.vates.wifibus.backoffice.model;

/**
 * Advertisement enums
 * 
 * @author luis.stubbia
 *
 */
public enum AdvertisementType {
	
	VIDEO("Video", VideoAd.class),
	BANNER("Banner", BannerAd.class);
	
	private final String displayName;
	private final Class<Advertisement> advertisementClass;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	AdvertisementType(String displayName, Class advertisementClass){
		this.displayName = displayName;
		this.advertisementClass = advertisementClass;
	}
	
    public Class<Advertisement> getAdvertisementClass() {
		return advertisementClass;
	}

	public String getDisplayName() {
        return displayName;
    }
	
	public Advertisement getInstance() {
		try {
			return this.advertisementClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns the Advertisement Type that matches the displayName.
	 *
	 * @param displayName
	 * @return AdvertisementType
	 */
	public static AdvertisementType lookupByName(String name) {
		if (name != null) {
			for (AdvertisementType b : AdvertisementType.values()) {
				if (name.equalsIgnoreCase(b.name())) {
					return b;
		        }
		    }
	    }
	    return null;
	}
}