package ca.sheridancollege.ramchrav.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import ca.sheridancollege.ramchrav.beans.Rewards;
import jakarta.servlet.http.HttpSession;

/**
 * This Controller class accesses the REST Controller using its URL and performs
 * database operations using the REST Controller connection.
 * 
 * @author Ravi Ramchand
 *
 */

@Controller
public class RewardWebController {
	public final String url = "http://localhost:8081/api/rewards/";

	/**
	 * @param model        sends data into the index template
	 * @param restTemplate access the REST Controller methods
	 * @return the home page that displays all the rewards and other data.
	 */
	@GetMapping("/")
	public String getHomePage(Model model, RestTemplate restTemplate) {
		ResponseEntity<Rewards[]> resp = restTemplate.getForEntity(url, Rewards[].class);
		model.addAttribute("rewards", resp.getBody());

		return "index.html";
	}

	/**
	 * 
	 * @param model sends an empty rewards object into the template to add a new
	 *              reward.
	 * @return addRewars page.
	 */
	@GetMapping("/addReward")
	public String addReward(Model model) {
		model.addAttribute("reward", new Rewards());
		return "addReward.html";
	}

	/**
	 * 
	 * @param reward       access the reward object from the addReward method above
	 *                     that contains the users input
	 * @param restTemplate access the REST Controller methods to add a new record
	 * @return a redirect to the home page
	 */
	@PostMapping("/processReward")
	public String processReward(@ModelAttribute Rewards reward, RestTemplate restTemplate) {
		restTemplate.postForLocation(url, reward);
		return "redirect:/";
	}

	/**
	 * 
	 * @param id           accesses the id in the link
	 * @param restTempalte access the REST Controller methods to edit a specific
	 *                     record
	 * @param model        sends a the reward record to edit into the template
	 * @return editReward page
	 */
	@GetMapping("/edit/{id}")
	public String editReward(@PathVariable Long id, RestTemplate restTempalte, Model model) {
		ResponseEntity<Rewards> reward = restTempalte.getForEntity(url + id, Rewards.class);
		System.out.println(reward.getBody());
		model.addAttribute("rewards", reward.getBody());
		return "editReward.html";
	}

	/**
	 * 
	 * @param reward       access the reward object from the editReward method above
	 *                     that contains the users input
	 * @param restTemplate access the REST Controller methods to take the record the
	 *                     user inputted and save it into the database
	 * @return redirect to the home page
	 */
	@PostMapping("/processEditedReward")
	public String processEditReward(@ModelAttribute Rewards reward, RestTemplate restTemplate) {
		restTemplate.put(url + reward.getId(), reward);
		return "redirect:/";
	}

	/**
	 * 
	 * @param id           accesses the id in the link
	 * @param restTemplate access the REST Controller methods to delete a specific
	 *                     record
	 * @return a redirect to the home page
	 */
	@GetMapping("/delete/{id}")
	public String deleteReward(@PathVariable Long id, RestTemplate restTemplate) {
		restTemplate.delete(url + id);
		return "redirect:/";
	}

	/**
	 * 
	 * @param restTemplate access the REST Controller methods to delete all records
	 * @return a redirect to the home page
	 */
	@GetMapping("/deleteAll")
	public String deleteReward(RestTemplate restTemplate) {
		restTemplate.delete(url);
		return "redirect:/";
	}

	/**
	 * 
	 * @param model        sends data into the index template
	 * @param restTemplate access the REST Controller methods
	 * @return home page
	 */
	@GetMapping("/randomReward")
	public String randomReward(Model model, RestTemplate restTemplate) {
		ResponseEntity<Rewards[]> respBody = restTemplate.getForEntity(url, Rewards[].class);
		model.addAttribute("rewards", respBody.getBody());
		ResponseEntity<Rewards> resp = restTemplate.getForEntity(url + "getRandom", Rewards.class);

		System.out.println(resp.getBody());
		model.addAttribute("randomReward", resp.getBody().getRewardName().toUpperCase());
		return "index.html";
	}

}
