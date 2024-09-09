package minionz.backend.scrum.label.model.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateLabelRequest {
    private Long workspaceId;
    private String labelName;
    private String description;
    private String color;
}