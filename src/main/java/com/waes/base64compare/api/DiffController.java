package com.waes.base64compare.api;

import com.waes.base64compare.domain.dto.ExceptionResponse;
import com.waes.base64compare.domain.dto.JsonData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@ApiResponses(value = {
        @ApiResponse(code = 400, message = "An error occurred", response = ExceptionResponse.class)
})
@RestController
@RequestMapping(value = "v1/diff", produces = "application/json")
@Validated
public class DiffController {

    @ApiOperation(value = "Send left side base64 json", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Left side base64 json successfully received")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/{id}/left", method = RequestMethod.POST, consumes = "application/json")
    ResponseEntity<?> sendLeft(@PathVariable("id") String id, @Valid @RequestBody JsonData body) throws URISyntaxException {
        return ResponseEntity.created(new URI(id)).body(null);
    }

    @ApiOperation(value = "Send right side base64 json", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Right side base64 json successfully received")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/{id}/right", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> sendRight(@PathVariable("id") String id, @Valid @RequestBody JsonData body) throws URISyntaxException {
        return ResponseEntity.created(new URI(id)).body(null);
    }

    @ApiOperation(value = "Get the result of comparison", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comparison done")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getResult(@PathVariable("id") String id) {
        return null;
    }
}
