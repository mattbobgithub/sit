package com.sit.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.TransferStatus;

/**
 * A DTO for the Transfer entity.
 */
public class TransferDTO implements Serializable {

    private Long id;

    private Long fromWorkroomId;

    private Long toWorkroomId;

    private TransferStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getFromWorkroomId() {
        return fromWorkroomId;
    }

    public void setFromWorkroomId(Long fromWorkroomId) {
        this.fromWorkroomId = fromWorkroomId;
    }
    public Long getToWorkroomId() {
        return toWorkroomId;
    }

    public void setToWorkroomId(Long toWorkroomId) {
        this.toWorkroomId = toWorkroomId;
    }
    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransferDTO transferDTO = (TransferDTO) o;

        if ( ! Objects.equals(id, transferDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
            "id=" + id +
            ", fromWorkroomId='" + fromWorkroomId + "'" +
            ", toWorkroomId='" + toWorkroomId + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
