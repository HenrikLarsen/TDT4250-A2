package tdt.unit.poundstokg;

import org.osgi.service.component.annotations.Component;

import tdt4250.unit.api.Unit;
import tdt4250.unit.util.UnitsConvert;

@Component(
		property = {
				UnitsConvert.UNIT_NAME_PROP + "=poundstokg",
				UnitsConvert.UNIT_CONVERSION_PROP + "=x/2.2046"}
		)
public class PoundsToKg extends UnitsConvert implements Unit {
}
