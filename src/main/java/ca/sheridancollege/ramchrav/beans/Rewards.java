package ca.sheridancollege.ramchrav.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rewards {
	private Long id;
	private String rewardName;
}
