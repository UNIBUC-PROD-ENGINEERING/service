package ro.unibuc.triplea.application.auth.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.triplea.application.auth.dto.response.StandardResponse;

@RestController
@RequestMapping("/api/v1/demo-controller")
@Tag(name = "Demo", description = "Demo management APIs")
public class DemoController {

    @Operation(
            summary = "Testing endpoint",
            description = "Testing endpoint for secured API calls",
            tags = {"demo", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity(new StandardResponse("200", "Done", "Hello from secured endpoint"), HttpStatus.OK);
    }

}