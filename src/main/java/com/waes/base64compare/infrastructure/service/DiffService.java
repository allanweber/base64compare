package com.waes.base64compare.infrastructure.service;

import com.waes.base64compare.domain.dto.Difference;
import com.waes.base64compare.domain.dto.DifferenceResponse;
import com.waes.base64compare.domain.entity.DiffEntity;
import com.waes.base64compare.domain.entity.Side;
import com.waes.base64compare.domain.exception.ApiException;
import com.waes.base64compare.domain.repository.IDiffRepository;
import com.waes.base64compare.domain.service.IDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Service responsible to manage Diff entities and theirs sides.
 */
@Service
public class DiffService implements IDiffService {

    private static final String message = "The entity %s does not have the %s side.";

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
     * Throws exception if id does not exist or if one side is null or empty.
     *
     * @param id is the is of the DiffEntity.
     * @return data regarding to difference evaluation.
     * @see com.waes.base64compare.domain.dto.DifferenceResponse
     */
    @Override
    public DifferenceResponse getDifferences(Long id) {
        DifferenceResponse response;
        response = selectById()
                .andThen(checkEntity(id))
                .andThen(checkLeft())
                .andThen(checkRight())
                .andThen(newDifferenceResponse())
                .andThen(getSidesArrays())
                .andThen(areEquals())
                .andThen(areSameSize())
                .apply(id);

        return response;
    }

    private Function<Long, DiffEntity> selectById() {
        return id -> repository.get(id);
    }

    private Function<DiffEntity, DiffEntity> checkEntity(Long id) {
        return entity -> Optional.ofNullable(entity).orElseThrow(
                () -> new ApiException(String.format("Diff %s id does not exist.", id)));
    }

    private Function<DiffEntity, DiffEntity> checkLeft() {
        return entity -> {
            if (StringUtils.isEmpty(entity.getSide(Side.Left))) {
                throw new ApiException(String.format(message, entity.getId(), Side.Left));
            }
            return entity;
        };
    }

    private Function<DiffEntity, DiffEntity> checkRight() {
        return entity -> {
            if (StringUtils.isEmpty(entity.getSide(Side.Right))) {
                throw new ApiException(String.format(message, entity.getId(), Side.Right));
            }
            return entity;
        };
    }

    private Function<DiffEntity, DifferenceResponse> newDifferenceResponse() {
        return entity ->
                new DifferenceResponse(entity.getId(), entity.getSide(Side.Left), entity.getSide(Side.Right));
    }

    private Function<DifferenceResponse, DifferenceResponse> getSidesArrays() {
        return response -> {
            byte[] left = Base64.getDecoder().decode(response.getLeft());
            byte[] right = Base64.getDecoder().decode(response.getRight());
            response.setSides(Arrays.asList(left, right));
            return response;
        };
    }

    private Function<DifferenceResponse, DifferenceResponse> areEquals() {
        return response -> {
            if (java.util.Arrays.equals(
                    response.getSides().get(Side.Left.value()),
                    response.getSides().get(Side.Right.value()))) {
                response.setEqual(true);
            }
            return response;
        };
    }

    private Function<DifferenceResponse, DifferenceResponse> areSameSize() {
        return response -> {
            if (response.getEqual()) return response;
            byte[] left = response.getSides().get(Side.Left.value());
            byte[] right = response.getSides().get(Side.Right.value());
            if (left.length == right.length) {
                //If sides are different but have same size, then compare to find differences.
                response.setEqualSize(true);
                List<Difference> differences = ArrayComparator.getDifferences(left, right);
                response.setDifferences(differences);
            }
            return response;
        };
    }
}
