package martin.td3spa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;
import io.github.jeemv.springboot.vuejs.utilities.JsArray;

@Controller
public class MainController {

	@Autowired
	private VueJS vue;
	
	@ModelAttribute(name = "vue")
    private VueJS getVue() {
        return this.vue;
    }

	private final String restOrgaURL = "'http://127.0.0.1:8091/orga/'";

	@RequestMapping("vue")
	public String index(ModelMap model) {
		vue.addDataRaw("orgas", "[]");
		vue.addDataRaw("orga", "{}");
		vue.addData("addDialog", false);
		
		
		
		vue.onBeforeMount("let self=this;" + Http.get(restOrgaURL, "self.orgas=response.data;"));
		
		vue.addMethod("postOrga", "let self=this;" + Http.post(restOrgaURL, "self.orga", "self.orgas.push(response.data);self.addDialog=false;", "console.log(response)"));
		
		vue.addMethod("deleteOrga", "let self=this;" + Http.delete(restOrgaURL + "+self.orga.id", JsArray.remove("self.orgas", "self.orga")));
		
		return "index";
	}
}