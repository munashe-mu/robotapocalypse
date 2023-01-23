package com.umlimi.robotapocalypse;

import com.umlimi.robotapocalypse.infectionrecord.InfectionRecordDto;
import com.umlimi.robotapocalypse.infectionrecord.InfectionRecordRequest;
import com.umlimi.robotapocalypse.infectionrecord.InfectionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@RestController
@RequestMapping("v1/robot/apocalypse")
@RequiredArgsConstructor
public class InfectionRecordRestController {
    private final InfectionRecordService infectionRecordService;

    @Operation(summary = "Report an Infection survivor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Infection Report saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InfectionRecordDto.class)) })})
    @PostMapping("save")
    public ResponseEntity<InfectionRecordDto> reportInfection(@RequestBody InfectionRecordRequest infectionRecordRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(infectionRecordService.reportInfection(infectionRecordRequest));
    }
}
