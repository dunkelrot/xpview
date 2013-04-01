package com.basf.xpview.model.report;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.basf.xpview.model.report.config.ConfigEntry;
import com.basf.xpview.model.report.config.Configuration;
import com.basf.xpview.model.report.config.ConfigurationList;
import com.basf.xpview.model.report.config.ObjectFactory;

public class ReportConfigurations {

	private static ReportConfigurations instance;

	public static ReportConfigurations getInstance() {
		if (instance == null) {
			instance = new ReportConfigurations();
		}
		return instance;
	}

	protected ConfigurationList configurationList;
	protected ObjectFactory factory;
	
	public ConfigurationList getConfigurationList() {
		return configurationList;
	}

	public ReportConfigurations() {
		this.factory = new ObjectFactory();
		this.configurationList = factory.createConfigurationList();
	}

	public void load() throws Exception {
		try {
			String fsp = System.getProperty("file.separator");
			String filePath = System.getProperty("user.home") + fsp + ".xpview" + fsp + "config.xml";
			File configFile = new File(filePath);
			if (configFile.exists()) {
				JAXBContext context = JAXBContext.newInstance(ConfigurationList.class);
				Unmarshaller um = context.createUnmarshaller();
				configurationList = (ConfigurationList) um
						.unmarshal(configFile);
			}
		} catch (JAXBException ex) {
			throw new Exception(ex);
		}
	}

	public void save() throws Exception {
		try {
			String fsp = System.getProperty("file.separator");
			String filePath = System.getProperty("user.home") + fsp + ".xpview";
			String fileName = filePath + fsp + "config.xml";

			new File(filePath).mkdirs();
			File configFile = new File(fileName);

			JAXBContext context = JAXBContext
					.newInstance(ConfigurationList.class);
			Marshaller mm = context.createMarshaller();
			mm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));

			mm.marshal(configurationList, new FileOutputStream(configFile));
		} catch (JAXBException ex) {
			throw new Exception(ex);
		}
	}

	public void save(String name, ReportPropertyData propertyData) {

		Configuration config = findConfiguration(name);
		if (config == null) {
			config = factory.createConfiguration();
			config.setName(name);
			configurationList.getConfiguration().add(config);
		}
		
		config.getConfigEntry().clear();
		for (ReportPropertyList propList : propertyData) {
			for (ReportProperty prop : propList) {
				if (prop.isChecked()) {
					ConfigEntry entry = factory.createConfigEntry();
					entry.setListName(propList.getName());
					entry.setPropertyName(prop.getProperty().getName());
					config.getConfigEntry().add(entry);
				}
			}
		}
	}

	public void load(String name, ReportPropertyData propertyData) {
		Configuration config = findConfiguration(name);
		if (config != null) {
			for (ConfigEntry entry : config.getConfigEntry()) {
				ReportPropertyList propList = propertyData.getByName(entry.getListName());
				if (propList != null) {
					ReportProperty reportProperty = propList.getByName(entry.getPropertyName());
					if (reportProperty != null) {
						reportProperty.setChecked(true);
					}
				}
			}
		}
	}

	protected Configuration findConfiguration(String name) {
		Configuration result = null;
		for (Configuration config : configurationList.getConfiguration()) {
			if (config.getName().equals(name)) {
				result = config;
				break;
			}
		}
		return result;
	}

}

