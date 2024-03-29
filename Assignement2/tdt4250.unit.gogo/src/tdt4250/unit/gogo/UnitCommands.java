package tdt4250.unit.gogo;

import org.osgi.service.component.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

import tdt4250.unit.api.Unit;
import tdt4250.unit.api.UnitSearchResult;
import tdt4250.unit.util.UnitsConvert;

// see https://enroute.osgi.org/FAQ/500-gogo.html

@Component(
		service = UnitCommands.class,
		property = {
			"osgi.command.scope=unit",
			"osgi.command.function=list",
			"osgi.command.function=convert",
			"osgi.command.function=add",
			"osgi.command.function=remove"
		}
	)
public class UnitCommands {

	private Configuration getConfig(String unitName) {
		try {
			Configuration[] configs = cm.listConfigurations("(&(" + UnitsConvert.UNIT_NAME_PROP + "=" + unitName + ")(service.factoryPid=" + UnitsConvert.FACTORY_PID + "))");
			if (configs != null && configs.length >= 1) {
				return configs[0];
			}
		} catch (IOException | InvalidSyntaxException e) {
		}
		return null;
	}

	@Descriptor("list available unitConversions")
	public void list() {
		System.out.print("Unitconversions: ");
		BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		try {
			for (ServiceReference<Unit> serviceReference : bc.getServiceReferences(Unit.class, null)) {
				Unit unit = bc.getService(serviceReference);
				try {
					if (unit != null) {
						System.out.print(unit.getUnitName());
					}
				} finally {
					bc.ungetService(serviceReference);
				}
				System.out.print(" ");
			}
		} catch (InvalidSyntaxException e) {
		}
		System.out.println();
	}

	@Descriptor("convert a number using a conversion")
	public void convert(
			@Descriptor("the unitconversion to use")
			String name,
			@Descriptor("the number to convert")
			String s
			
			) {
		BundleContext bc = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		try {
			// iterate through all Unit service objects
			boolean found = false;
			for (ServiceReference<Unit> serviceReference : bc.getServiceReferences(Unit.class, null)) {
				Unit unit = bc.getService(serviceReference);
				if (unit != null) {
					if(name.equals(unit.getUnitName())) {
						found = true;
						try {
							UnitSearchResult search = unit.convert(s);
							System.out.println(search.getMessage());
						} finally {
							bc.ungetService(serviceReference);
						}
					}
				} else {
					System.out.println(serviceReference.getProperties());
				}
			}
			if(found == false) {
				System.out.println("Sorry, no conversions found with that name.");
			}
		} catch (InvalidSyntaxException e) {
		}
	}

	@Reference(cardinality = ReferenceCardinality.MANDATORY)
	private ConfigurationAdmin cm;

	@Descriptor("add a conversion, with content a equation")
	public void add(
			@Descriptor("the name of the new unitconversion")
			String unitName,
			@Descriptor("The equation")
			String equation
			) throws IOException, InvalidSyntaxException {
		
		String actionName = "updated";
		// lookup existing configuration
		Configuration config = getConfig(unitName);
		if (config == null) {
			// create a new one
			config = cm.createFactoryConfiguration(UnitsConvert.FACTORY_PID, "?");
			actionName = "added";
		}
		Dictionary<String, String> props = new Hashtable<>();
		props.put(UnitsConvert.UNIT_NAME_PROP, unitName);
		props.put(UnitsConvert.UNIT_CONVERSION_PROP, equation);
		
		config.update(props);
		System.out.println("\"" + unitName + "\" dictionary " + actionName);
	}

	@Descriptor("remove a (manually added) conversion")
	public void remove(
			@Descriptor("the name of the (manually added) conversion to remove")
			String name
			) throws IOException, InvalidSyntaxException {
		Configuration config = getConfig(name);
		boolean removed = false;
		if (config != null) {
			config.delete();
			removed = true;
		}
		System.out.println("\"" + name + "\" conversion " + (removed ? "removed" : "was not added manually"));
	}
}
