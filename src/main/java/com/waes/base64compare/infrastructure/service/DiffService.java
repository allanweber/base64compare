package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.domain.dto.Difference;
import com.waes.base64compare.domain.dto.DifferenceResponse;
import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.repository.IDiffRepository;
import com.waes.base64compare.domain.service.IDiffService;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service responsible to manage Diff entities and theirs sides
 */
@Service
public class DiffService implements IDiffService {

    /**
     * Repository to save diff entity
     */
    private IDiffRepository repository;

    /**
     * Only possible constructor for the class
     *
     * @param repository is an instance of IDiffRepository, if null a NullPointerException will be thrown
     */
    @Autowired
    public DiffService(IDiffRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DiffRepository is a required parameter.");
    }

    /**
     * Save the left side of the DiffEntity
     *
     * @param id     is the is of the DiffEntity
     * @param base64 is the value of base64 to the left side
     */
    @Override
    public void saveLeft(Long id, String base64) {
        DiffEntity entity = Optional.ofNullable(repository.get(id)).orElse(new DiffEntity(id));
        entity.setSide(Side.Left, base64);
        repository.save(entity);
    }

    /**
     * Save the left side of the DiffEntity
     *
     * @param id     is the is of the DiffEntity
     * @param base64 is the value of base64 to the right side
     */
    @Override
    public void saveRight(Long id, String base64) {
        DiffEntity entity = Optional.ofNullable(repository.get(id)).orElse(new DiffEntity(id));
        entity.setSide(Side.Right, base64);
        repository.save(entity);
    }

    /**
     * Return the result of the comparison of the sides of the DiffEntity
     *
     * @param id is the is of the DiffEntity
     * @return the result of the comparison
     */
    @Override
    public DifferenceResponse getDifferences(Long id) {
        DiffEntity entity = repository.get(id);

        String message = "The entity %s does not have the %s side.";

        String leftBase64 = entity.getSide(Side.Left);
        String rightBase64 = entity.getSide(Side.Right);

        Objects.requireNonNull(leftBase64, String.format(message, id, Side.Left));
        Objects.requireNonNull(rightBase64, String.format(message, id, Side.Right));

        byte[] left = Base64.getDecoder().decode(leftBase64);
        byte[] right = Base64.getDecoder().decode(rightBase64);

        DifferenceResponse response = new DifferenceResponse(id, leftBase64, rightBase64);

        if (java.util.Arrays.equals(left, right)) {
            response.setEqual(true);
        } else if (left.length == right.length) {
            response.setEqualSize(true);
            List<Difference> differences = getDifferences(left, right);
            response.setDifferences(differences);
        }

        return response;
    }

    private List<Difference> getDifferences(byte[] left, byte[] right) {

        List<Difference> differences = new ArrayList<>();
        int offSetStart = -1;
        int offSetSize = 1;

        for (int i = 0; i < left.length; i++) {

            if (left[i] != right[i]) {
                if (offSetStart == -1) {
                    offSetStart = i;
                } else {
                    offSetSize++;
                }
            } else if (i == (left.length - 1) && offSetStart != -1) {
                differences.add(new Difference(offSetStart, offSetSize));
            } else {
                if (offSetStart != -1) {
                    differences.add(new Difference(offSetStart, offSetSize));
                    offSetSize = 1;
                    offSetStart = -1;
                }
            }
        }

        return differences;
    }
}
