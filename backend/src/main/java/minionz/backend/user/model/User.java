package minionz.backend.user.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.chat_participation.model.ChatParticipation;
import minionz.backend.scrum.issue_participation.model.IssueParticipation;
import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import minionz.backend.scrum.sprint_participation.model.SprintParticipation;
import minionz.backend.scrum.workspace_participation.model.WorkspaceParticipation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String Id;
    private String password;
    private String userName;
    private Integer loginType;
    private boolean isVeryfied;
    private String email;
    private String createdAt;
    private String userRole;

    // ChatParticipation 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatParticipation> chatParticipations = new ArrayList<>();

    // User : WorkspaceParticipation = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkspaceParticipation> workspaceParticipations = new ArrayList<>();

    // User : SprintParticipation = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SprintParticipation> sprintParticipations = new ArrayList<>();

    // User : IssueParticipation = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IssueParticipation> issueParticipations = new ArrayList<>();

    // User : MeetingParticipation = 1 : N
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MeetingParticipation> meetingParticipations = new ArrayList<>();
}
