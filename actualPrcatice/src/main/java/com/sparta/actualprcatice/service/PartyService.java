package com.sparta.actualprcatice.service;

import com.sparta.actualprcatice.dto.request.PartyRequestDto;
import com.sparta.actualprcatice.dto.response.PartyResponseDto;
import com.sparta.actualprcatice.entity.Admin;
import com.sparta.actualprcatice.entity.Member;
import com.sparta.actualprcatice.entity.MemberParty;
import com.sparta.actualprcatice.entity.Party;
import com.sparta.actualprcatice.repository.AdminRepository;
import com.sparta.actualprcatice.repository.MemberPartyRepository;
import com.sparta.actualprcatice.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final AdminRepository adminRepository;

    private final MemberPartyRepository memberPartyRepository;


    public ResponseEntity<?> createParty(PartyRequestDto partyRequestDto, Member member) {

        Party party = new Party(partyRequestDto);
        Admin admin = new Admin(member,party);
        MemberParty memberParty = new MemberParty(member, party);

        partyRepository.save(party);
        adminRepository.save(admin);
        memberPartyRepository.save(memberParty);

        return new ResponseEntity<>("그룹이 생성되었습니다.", HttpStatus.OK);
    }
    

    public ResponseEntity<?> getPartyList(Member member) {

       List<MemberParty> memberPartyList = memberPartyRepository.findAllByMember_Id(member.getId());
       List<PartyResponseDto> partyResponseDtoList = new ArrayList<>();
       List<Party> partyList = new ArrayList<>();

       for(MemberParty memberParty : memberPartyList) {
           Party party = partyRepository.findById(memberParty.getId()).orElseThrow(() -> new NullPointerException("해당 그룹이 없습니다"));
           partyList.add(party);
       }

       for(Party party : partyList) {
           partyResponseDtoList.add(new PartyResponseDto(party));
       }

       return new ResponseEntity<>(partyResponseDtoList, HttpStatus.OK);

    }



    public boolean validateMember(Member member, Party party) {

        boolean result = true;

        for(int i = 0; i < party.getMemberPartyList().size(); i++ ){
            if( !member.getEmail().equals(party.getMemberPartyList().get(i).getMember().getEmail())) {
                result = false;
            }
        }

        return result;
    }


}
