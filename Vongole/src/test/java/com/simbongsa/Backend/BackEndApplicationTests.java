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


}
