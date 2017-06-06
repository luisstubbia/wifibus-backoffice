package com.vates.wifibus.backoffice.model;

/**
 * Advertisement enums
 * 
 * @author luis.stubbia
 *
 */
public enum AdvertisementType {
	
	VIDEO("Video", VideoAdForm.class, "fragments/advertisementVideoForm"),
	BANNER("Banner", BannerAdForm.class, "fragments/advertisementBannerForm");
	
	private final String displayName;
	private final Class<AdvertisementForm<?,?>> advertisementClass;
	private final String view;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	AdvertisementType(String displayName, Class advertisementClass, String view){
		this.displayName = displayName;
		this.advertisementClass = advertisementClass;
		this.view = view;
	}
	
    public Class<AdvertisementForm<?,?>> getAdvertisementClass() {
		return advertisementClass;
	}

	public String getDisplayName() {
        return displayName;
    }
	
	public String getView() {
		return this.view;
	}
	
	public AdvertisementForm<?,?> getInstance() {
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