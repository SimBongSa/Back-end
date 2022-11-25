package com.simbongsa.Backend;

import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.shared.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@Transactional
class BackEndApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {

	}

//	@Test
//	void 일반멤버랜덤아이디() {
//
//		List<String> emailAddress = new ArrayList<>(Arrays.asList("@naver.com","@google.com","@nate.com","@hanghae.com"));
//		List<String> name = new ArrayList<>(Arrays.asList("강동원","차승원","차은우","윤지용","남병관","고승유","지석진",
//				"박효신","강창식","김성호","김성민","김원재","강진구","장석원","김경일","이효리","이솜","박은진","이일민","김영한"));
//
//
//		List<String> gender = new ArrayList<>(Arrays.asList("male","female"));
//
//
//		for (int i = 0; i < 20; i++) {
//			// 아이디 / 비밀번호
//			StringBuilder id = new StringBuilder("member" + String.format("%02d",i));
//
//			// email
//			int index = (int) (Math.random() * 4);
//
//			// phoneNumber
//			StringBuilder phoneNumber = new StringBuilder("010-");
//			int middleNumber = (int) (Math.random() * 9000) + 1000;
//			int lastNumber = (int) (Math.random() * 9000) + 1000;
//			phoneNumber.append(middleNumber).append("-").append(lastNumber);
//
//			// gender
//			int idx = (int) (Math.random() * 2);
//
//			// birthdate
//			LocalDate start = LocalDate.of(1980, Month.JANUARY, 1);
//			LocalDate end = LocalDate.of(2005, Month.JANUARY, 1);
//
//			long days = ChronoUnit.DAYS.between(start, end);
//			LocalDate randomDate = start.plusDays(new Random().nextInt((int) days + 1));
//
//			// authority
//			String authority = "ROLE_MEMBER";
//
//			Member member = Member.builder()
//					.username(id.toString())
//					.password(id.toString())
//					.name(name.get(i))
//					.email(id.append(emailAddress.get(index)).toString())
//					.phoneNumber(phoneNumber.toString())
//					.gender(gender.get(idx))
//					.birthdate(randomDate)
//					.authority(Authority.valueOf(authority))
//					.build();
//
//			em.persist(member);
//		}
//
//
//	}

}
