// Licence
package tom.mapping.dsl.generator

import org.eclipse.emf.ecore.EPackage
import tom.mapping.model.Mapping

class ImportsCompiler {

	extension TomMappingExtensions = new TomMappingExtensions()
	
	
	def imports(Mapping map) {
		for(p: map.getAllRootPackages) {
			imports(p.getNsPrefix, p);
		}
	}
	
	
	def importsWithUtils(Mapping map) {
		for(p: map.getAllRootPackages) {
			importsWithUtils(p.getNsPrefix, p);
		}
	}
	
	
	def imports(String prefix, EPackage ep) { // Is the '''import''' correct ?
		if(ep.EClassifiers.size > 0) {
		var aimporter = prefix+ep.name+".*";
			'''
			 import �aimporter»; 
			 '''
		}
		for(p: ep.ESubpackages) {
			imports(p.getNsPrefix, p);
		}
	}


	def importsWithUtils(String prefix, EPackage ep) {
		if(ep.EClassifiers.size > 0) {
			var import1 = prefix+ep.name+".*";
			var import2 = prefix+ep.name+".util.*";
			'''
			import �import1»; 
			import �import2»; 
			'''
		}
		for(p: ep.ESubpackages) {
			importsWithUtils(p.getNsPrefix, p);
		} 
	}


}