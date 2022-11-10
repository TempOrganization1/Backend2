package com.sparta.actualpractice.entity;

import com.sparta.actualpractice.dto.request.ScheduleRequestDto;
import com.sparta.actualpractice.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Schedule{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTY_ID")
    private Party party;

    @OneToMany(mappedBy = "schedule")
    private List<Participant> participantList;

    public Schedule(Member member, ScheduleRequestDto scheduleRequestDto, Party party) {

        this.title = scheduleRequestDto.getTitle();
        this.content = scheduleRequestDto.getContent();
        this.time = scheduleRequestDto.getDate() + " " + scheduleRequestDto.getMeetTime();
        this.place = scheduleRequestDto.getPlace().getAddress() + "," + scheduleRequestDto.getPlace().getPlaceName();
        this.member = member;
        this.party = party;
    }
}
