package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryVOTest {

    MemberRepositoryVO repository = new MemberRepositoryVO();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV100", 10000);
        repository.save(member);

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        // 비교값 false
        log.info("member == findMember {}", member == findMember);
        //equals hashcode lombok의 @data를 이용해서 셋팅
        log.info("member equals findMember {}", member.equals(findMember));
        //assertj << 이용,
        assertThat(findMember).isEqualTo(member);

        //update: money: 10000 -> 20000
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        //테스트 코드 중간에 예외가 발생시 delete테스트 및 다른 로직을 실행 할수 없다

        //delete
        repository.delete(member.getMemberId());
        //findById로 호출하면 assertThat 예외가 발생 NoSuchElementException 이 터진다
        Assertions.assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
        //이미삭제 되었기 때문에 이방식으로 검증불가
        //Member deleteMember = repository.findById(member.getMemberId());
    }
}