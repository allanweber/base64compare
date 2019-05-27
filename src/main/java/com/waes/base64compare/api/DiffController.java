package com.waes.base64compare.api;

import com.waes.base64compare.domain.dto.DifferenceResponse;
import com.waes.base64compare.domain.dto.ExceptionResponse;
import com.waes.base64compare.domain.dto.JsonData;
import com.waes.base64compare.infrastructure.service.DiffService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Controller responsible for manage the comparison between base64.
 */
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "An error occurred", response = ExceptionResponse.class)
})
@RestController
@RequestMapping(value = "v1/diff", produces = "application/json")
@Validated
public class DiffController {

    /**
     * Service to manage the differences.
     */
    private DiffService service;

    /**
     * Only possible constructor setting all dependencies. If any dependency was null a NullPointerException will be thrown.
     * @param service is the DiffService to manage difference process.
     */
    @Autowired
    public DiffController(DiffService service) {
        this.service = Objects.requireNonNull(service, "DiffService is a required dependency.");
    }

    /**
     * Responsible for receive the left side of comparison.
     *
     * @param id   is the is of transaction, you need the same id for left and right sides.
     * @param body contains the base64 string to compare.
     * @see com.waes.base64compare.domain.dto.JsonData
     * @return the response with status 201 and the same data sent in body in success case or status 400 and exception message in case of fail.
     * @throws URISyntaxException
     */
    @ApiOperation(value = "Send left side base64 json", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Left side base64 json successfully received")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/{id}/left", method = RequestMethod.POST, consumes = "application/json")
    ResponseEntity<?> sendLeft(@PathVariable("id") long id, @Valid @RequestBody JsonData body) throws URISyntaxException {

        service.saveLeft(id, body.getBase64());
        return ResponseEntity.created(new URI(String.valueOf(id))).body(body);
    }

    /**
     * Responsible for receive the right side of comparison.
     *
     * @param id  is the is of transaction, you need the same id for left and right sides.
     * @param body contains the base64 string to compare.
     * @see com.waes.base64compare.domain.dto.JsonData
     * @return the response with status 201 and the same data sent in body in success case or status 400 and exception message in case of fail.
     * @throws URISyntaxException
     */
    @ApiOperation(value = "Send right side base64 json", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Right side base64 json successfully received")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/{id}/right", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> sendRight(@PathVariable("id") Long id, @Valid @RequestBody JsonData body) throws URISyntaxException {

        service.saveRight(id, body.getBase64());
        return ResponseEntity.created(new URI(String.valueOf(id))).body(body);
    }

    /**
     * Responsible for return the comparison status of left and right sides.
     * @param id is the is of transaction, you need the same id for left and right sides.
     * @return the response status 200 with DifferenceResponse object in body response or status 400 and exception message in case of fail.
     */
    @ApiOperation(value = "Get the result of comparison", response = DifferenceResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comparison done")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getResult(@PathVariable("id") long id) {

        return ResponseEntity.ok(service.getDifferences(id));
    }
}
