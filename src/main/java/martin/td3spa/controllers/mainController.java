package martin.td3spa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;

@Controller
public class mainController {

	@Autowired
	private VueJS vue;

	private String restOrgaURL = "http://127.0.0.1:8091/orga";
	// private String restUserURL = "http://127.0.0.1:8091/user";

	@RequestMapping("/vue")
	public String index(ModelMap model) {
		// vue.addData("message", "Hello world !");

		vue.addDataRaw("orgas", "[]");
		vue.addDataRaw("newOrga", "{}");
		vue.addData("addDialog", false);

		vue.onBeforeMount("let self=this;" + Http.get(restOrgaURL, "self.orgas=response.data;"));
		model.put("vue", vue);
		
		vue.addMethod("addOrga", "let self=this;" + Http.post(restOrgaURL, "self.newOrga", "self.orgas.push(response.data);self.addDialog=false;", "console.log(response)"));		
		return "index";
	}
}
