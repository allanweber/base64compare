package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.domain.dto.Difference;
import com.waes.base64compare.domain.dto.DifferenceResponse;
import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.repository.IDiffRepository;
import com.waes.base64compare.domain.service.IDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service responsible to manage Diff entities and theirs sides.
 */
@Service
public class DiffService implements IDiffService {

    /**
     * Repository to save diff entity.
     */
    private IDiffRepository repository;

    /**
     * Only possible constructor for the class.
     *
     * @param repository is an instance of IDiffRepository, if null a NullPointerException will be thrown.
     */
    @Autowired
    public DiffService(IDiffRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DiffRepository is a required parameter.");
    }

    /**
     * Save the left side of the DiffEntity.
     *
     * @param id     is the is of the DiffEntity.
     * @param base64 is the value of base64 to the left side.
     */
    @Override
    public void saveLeft(Long id, String base64) {
        DiffEntity entity = Optional.ofNullable(repository.get(id)).orElse(new DiffEntity(id));
        entity.setSide(Side.Left, base64);
        repository.save(entity);
    }

    /**
     * Save the left side of the DiffEntity.
     *
     * @param id     is the is of the DiffEntity.
     * @param base64 is the value of base64 to the right side.
     */
    @Override
    public void saveRight(Long id, String base64) {
        DiffEntity entity = Optional.ofNullable(repository.get(id)).orElse(new DiffEntity(id));
        entity.setSide(Side.Right, base64);
        repository.save(entity);
    }

    /**
     * Return the result of the comparison of the sides of the DiffEntity.
     *
     * @param id is the is of the DiffEntity.
     * @return data regarding to difference evaluation.
     * @see com.waes.base64compare.domain.dto.DifferenceResponse
     */
    @Override
    public DifferenceResponse getDifferences(Long id) {
        DiffEntity entity = repository.get(id);

        String message = "The entity %s does not have the %s side.";

        //Get both sides from DiffEntity
        String leftBase64 = entity.getSide(Side.Left);
        String rightBase64 = entity.getSide(Side.Right);

        //Validate if both sides are fill or throw a exception if any are not fill.
        Objects.requireNonNull(leftBase64, String.format(message, id, Side.Left));
        Objects.requireNonNull(rightBase64, String.format(message, id, Side.Right));

        //Convert both sides to byte[] to check their equality or differences.
        byte[] left = Base64.getDecoder().decode(leftBase64);
        byte[] right = Base64.getDecoder().decode(rightBase64);

        //Create basic response with common data.
        //If not enter in any if statement below, then return that both sides are not equals and not have equal sizes.
        DifferenceResponse response = new DifferenceResponse(id, leftBase64, rightBase64);

        //If both sides are equal, only set it and finish the comparison.
        if (java.util.Arrays.equals(left, right)) {
            response.setEqual(true);
        } else if (left.length == right.length) {
            //If sides are different but have same size, then compare to find differences.
            response.setEqualSize(true);
            List<Difference> differences = ArrayComparator.getDifferences(left, right);
            response.setDifferences(differences);
        }

        return response;
    }


}
