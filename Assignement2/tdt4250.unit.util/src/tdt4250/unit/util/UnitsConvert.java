package tdt4250.unit.util;


import java.util.HashMap;
import java.util.Map;

import javax.script.*;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import tdt4250.unit.api.Unit;
import tdt4250.unit.api.UnitSearchResult;

@Component(
		configurationPid = UnitsConvert.FACTORY_PID,
		configurationPolicy = ConfigurationPolicy.REQUIRE
		)
public class UnitsConvert implements Unit {
	public static final String FACTORY_PID = "tdt4250.unit.util.UnitsConvert";
	
	public static final String UNIT_CONVERSION_PROP = "unitConversion";
	public static final String UNIT_NAME_PROP = "unitName";
	
	private String name;
	private String conversion;
	
	@Override
	public String getUnitName() {
		return name;
	}

	protected void setUnitName(String name) {
		this.name = name;
	}
	
	protected void setConversion(String conversion) {
		this.conversion = conversion;
	}
	
	public @interface UnitsConvertConfig {
		String unitName();
		String unitConversion() default "";
	}

	@Activate
	public void activate(BundleContext bc, UnitsConvertConfig config) {
		update(bc, config);
	}

	@Modified
	public void modify(BundleContext bc, UnitsConvertConfig config) {
		update(bc, config);		
	}

	protected void update(BundleContext bc, UnitsConvertConfig config) {
		setUnitName(config.unitName());
		setConversion(config.unitConversion());
	}

	protected String getSuccessMessageStringFormat() {
		return this.name + ": %s was converted to: %.2f " ;
	}

	protected String getFailureMessageStringFormat(String error) {
		return this.name + ": Could not convert %s \n"+ error;
	}
	
	public UnitSearchResult convert(String convertNumber) {
        Map<String, Object> vars = new HashMap<String, Object>();
		
        try {
			vars.put("x", Double.parseDouble(convertNumber));
		}catch(NumberFormatException e) {
			return new UnitSearchResult(false, String.format(getFailureMessageStringFormat("Bad input format. Input should be a number."), convertNumber), null);
		}

		try {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
			double result  =  (double) engine.eval(this.conversion, new SimpleBindings(vars));		
			return new UnitSearchResult(true, String.format(getSuccessMessageStringFormat(), convertNumber, (float)result), null);
		}catch(ScriptException io) {
			return new UnitSearchResult(false, String.format(getFailureMessageStringFormat(""), convertNumber), null);
		}
		
	}
}
