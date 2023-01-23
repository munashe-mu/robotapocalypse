package com.umlimi.robotapocalypse.robot.impl;

import com.umlimi.robotapocalypse.Constants;
import com.umlimi.robotapocalypse.robot.Robot;
import com.umlimi.robotapocalypse.robot.RobotDto;
import com.umlimi.robotapocalypse.robot.RobotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.naming.CommunicationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MUNASHE MURIMI
 * @created 19/01/2023
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class RobotServiceImpl implements RobotService {
    private final WebClient webClient;

    @Override
    public RobotDto listAllRobots() throws CommunicationException {
        Robot[] robotsArray = webClient.get()
                .uri(Constants.ROBOTS_URL)
                .retrieve()
                .bodyToMono(Robot[].class)
                .block();

        List<Robot> flyingRobots = Arrays.stream(robotsArray)
                .filter(robot -> robot.getCategory().equalsIgnoreCase("Flying"))
                .collect(Collectors.toList());

        List<Robot> landRobots = Arrays.stream(robotsArray)
                .filter(robot -> robot.getCategory().equalsIgnoreCase("Land"))
                .collect(Collectors.toList());

        RobotDto robotDto = RobotDto.builder()
                .flyingRobots(flyingRobots)
                .landRobots(landRobots)
                .numberOfFlyingRobots(flyingRobots.stream().count())
                .numberOfLandRobots(landRobots.stream().count())
                .build();
        return robotDto;
    }
}
