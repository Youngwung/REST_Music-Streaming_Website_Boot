# REST_Music-Streaming_Website_Boot

![1_썸네일](https://github.com/user-attachments/assets/41906ead-9007-48bf-abe7-cd4536b6d0ab)


# 📋 프로젝트 개요

- **개발 기간**:
    - 2024.08.13 ~ 2024.09.09
- **목표:**
    - Spring Boot 기반으로 현대화 및 성능 최적화를 진행하며, 기존 프로젝트의 문제점을 보완하고 추가 기능을 개발
- **팀 구성**: 6명 [팀장]
- **역할**
    - 데이터 베이스 설계 및 수정/보완 [기여도 100%]
        - 데이터베이스 수정/보완
    - 음악 재생 시스템 구현 [기여도 100%]
        - boot 마이그레이션
    - 앨범 상세 페이지 구현 [기여도 100%]
        - boot 마이그레이션 및 DB 수정에 따른 리팩토링
    - 검색 쿼리 최적화 [기여도 100%]
        - 팀원의 로직 기반 쿼리의 성능을 최적화
    - 초성 검색 기능 개발 [기여도 80%]
        - 초성 검색의 로직 등 백엔드 개발 담당
- **GitHub**:
    - **Phase 1:** [Spring MVC 기반 프로젝트](https://github.com/Youngwung/REST_Music-Streaming_Website) (Phase 1 GitHub링크)
    - **Phase 2:** [Spring Boot 기반 프로젝트](https://github.com/Youngwung/REST_Music-Streaming_Website_Boot) (현재 Repo)

# 🔧 기술 스택

- **공통:** HTML, CSS, JS, Bootstrap
- Spring Boot, Thymeleaf, JPA, MySQL

# 🚀 주요 기능

### **음악 스트리밍**

- **음악 재생 시스템 [담당]**
    - 음악 스트리밍 및 재생 제어
    - 재생 목록 관리
    - 재생 목록 조회 기능
    - 페이지 전환에도 재생이 중단되지 않음

### **콘텐츠 관리**

- **앨범**
    - 앨범 상세 페이지 **[담당]**
        - 앨범 정보 조회
        - 수록곡 목록 제공
    - 앨범 리스트 페이지
        - 최신/인기 앨범 목록
        - 카테고리별 분류
- **음원**
    - 음원 상세 페이지
        - 음원 정보 조회
        - 아티스트/앨범 연계 정보
- **아티스트/그룹**
    - 아티스트 상세 페이지
        - 프로필 및 소개
        - 발매 앨범/음원 목록
    - 그룹 상세 페이지
        - 그룹 정보
        - 멤버 정보
        - 발매 앨범/음원 목록

### 검색 시스템

- **통합 검색 [담당: 쿼리 최적화]**
    - 앨범/음원/아티스트 통합 검색
    - 카테고리 별 검색 결과 제공
- **초성 검색 [담당: 백엔드 개발]**
    - 한글 초성 기반 검색어 추천

### 회원 서비스

- **계정 관리**
    - 회원가입/로그인
    - 프로필 관리
- **플레이 리스트**
    - 개인 플레이리스트 생성/수정/삭제 기능 제공

# 👨‍💻 담당 업무

## 1. 데이터베이스 설계 및 수정/보완

## 데이터베이스 수정/보완

### 데이터베이스 설계 문제점 분석

**그룹 아티스트 관리 불가**

**설계 문제점:**

- 그룹으로 활동하는 아티스트 정보 관리 불가
- 한 아티스트의 솔로/그룹 활동 이력 관리 어려움

**문제 발생 원인:**

- 설계 단계에서 솔로/그룹 활동의 분리 필요성을 간과

**적용된 개선 사항:**

```sql
-- 그룹 정보 관리를 위한 테이블 추가
CREATE TABLE GROUPS (
    group_id INT PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL,
    group_image VARCHAR(200)
);

-- 그룹 멤버 관리 테이블
CREATE TABLE GROUP_MEMBERS (
    group_id INT,
    artist_id INT,
    FOREIGN KEY (group_id) REFERENCES GROUPS(group_id),
    FOREIGN KEY (artist_id) REFERENCES ARTISTS(artist_id)
);

-- ARTIST_ROLES 테이블에 그룹 관계 추가
ALTER TABLE ARTIST_ROLES
ADD COLUMN group_id INT,
ADD FOREIGN KEY (group_id) REFERENCES GROUPS(group_id);
```

**기대 효과:**

- 솔로/그룹 활동의 분리
- 아티스트 정보 만으로 그룹 활동 추적 가능
- 그룹 멤버의 역할 참여 추적 가능

**적용 결과:**

- 그룹 볼빨간 사춘기의 멤버 안지영의 아티스트 페이지:
    - 그룹으로 참여한 음원을 아티스트 페이지에 출력할 수 있게 되었음
        
        ![2_기대효과](https://github.com/user-attachments/assets/ff6af2e4-0423-4721-aff7-4aaa9de0087a)
        

### 설계 및 보완 과정을 통해 배운 점

1. **설계 단계에서의 깊은 고민 필요**
    - 단순히 현재 요구사항을 충족시키는 것을 넘어서는 고민 필요
    - 향후 발생할 수 있는 변경사항과 확장 가능성 고려
    - 데이터베이스 설계 표준과 패턴에 대한 학습 중요성 인식
2. **리팩토링의 어려움 인식**
    - 데이터베이스 구조 변경은 높은 비용과 위험이 수반
    - 초기 설계의 중요성과 신중한 접근의 필요성 체득

## 2. 음악 재생 시스템 구현

프로젝트의 핵심 기능인 음악 재생 시스템을 구현했습니다. 

### 기술 스택

`Spring MVC` `Spring Boot` `JavaScript` `bootstrap`

### 주요 기능 및 구현 내용

- **끊김 없는 음악 재생 구현**
    - `frameset` 태그를 활용하여 재생 영역과 컨텐츠 영역을 분리
    - 페이지 전환 시에도 재생 중인 음악이 중단되지 않도록 설계
    - 재생 영역의 독립성을 보장하여 안정적인 스트리밍 서비스 제공
- **재생 목록 관리 시스템**
    - `sessionStorage`를 활용한 재생 정보 관리
        - 현재 재생 중인 음악 정보 저장
        - 재생 목록의 추가/삭제 기능 구현
        - 세션 유지 중 재생 정보 보존
    - Bootstrap modal을 활용한 직관적인 재생 목록 UI 구현
        - 현재 재생 목록 조회 기능
        - 재생 순서 확인 및 관리 인터페이스 제공
- **음악 재생 컨트롤러 구현**
    - HTML5 Audio API를 활용한 재생 제어
        - 재생/일시정지/정지 기능 구현
        - 현재 재생 시간 실시간 업데이트
        - 음량 조절 기능 구현
    - Bootstrap Progress bar를 활용한 재생 진행 상태 시각화
        - 전체 재생 시간 대비 현재 재생 시간 표시
        - 드래그를 통한 재생 구간 이동 기능 구현
- **구현 내용 스크린샷**
    - 음원 상세 페이지 및 재생 페이지
    
    ![3_재생페이지](https://github.com/user-attachments/assets/04743a69-2e67-4c16-b990-691fa9e67c6e)
    
    - 대기 목록 modal 창
    
    ![4_모달](https://github.com/user-attachments/assets/89cbd7c4-a718-43f7-876b-55359b3d59d7)

    
### 기술적 도전 과제와 해결 과정

**프레임 분리 환경에서의 모달 창 관리 이슈**

- **도전 과제**
    - frameset으로 분리된 재생 영역과 컨텐츠 영역 간의 모달 창 동작 제어
    - 재생 영역의 버튼으로 컨텐츠 영역에 모달 창을 표시하는 구조
    - 세션 스토리지에 저장된 재생 목록 데이터를 모달 창에 표시
- **문제 상황**
    - 모달 창을 닫은 후에도 배경 딤(dim) 처리와 클릭 방지 레이어가 간헐적으로 제거되지 않는 현상 발생
    - 비정기적으로 발생하는 특성으로 인해 원인 파악이 어려움
- **원인 분석**
    - 재생 영역(버튼)과 컨텐츠 영역(모달 창)이 분리되어 있어 일반적인 모달 창 동작 방식과 차이 발생
    - 모달 창이 활성화된 상태에서도 분리된 영역의 버튼이 클릭 가능한 상태로 유지
    - 버튼 클릭 시마다 새로운 배경 레이어가 생성되어 누적되는 현상 확인
- **해결 방안**
    - 전역 변수 `isModalOpen`을 도입하여 모달 창의 상태 관리
    
    ```jsx
    	let isModalOpen = false;
    
    	function showModal() {
    		if (isModalOpen) {
    			// console.log('Modal is already open.');
    			// 모달 창이 활성화되어 있는 경우
    			return;
    			// 함수 종료
    		}
    		
    		// 모달 창 표시 로직
    
    		// 모달이 열릴 때 상태 업데이트
    		isModalOpen = true;
    		
    		// 모달 창 종료 시 실행되는 이벤트 리스너
    		cPListModal.addEventListener('hidden.bs.modal', () => {
    			// console.log('모달창 닫힐 때 실행.');
    			
    			// 모달이 닫힐 때 상태 업데이트
    			isModalOpen = false;
    		});
    ```
    
    - 모달 창 상태를 추적하여 중복 실행 방지
    - 모달 창 종료 시 상태 변수 초기화로 안정적인 동작 보장
- **결과**
    - 모달 창의 안정적인 동작 확보
    - 배경 레이어 누적 문제 해결
    - 프레임 분리 환경에서도 일관된 사용자 경험 제공

### 협업 및 재생 기능 연동

- **협업 과제**
    - 여러 팀원이 개발하는 페이지와의 효율적인 연동 방안 필요
- **해결 방안**
    - 팀 내 표준 HTML 속성 규약 수립
    
    ```html
       <!-- 음원 재생 버튼 표준 마크업 -->
       <button id="listenBtn" data-id="${songId}" title="재생" class="btn">
         바로 듣기 버튼 이미지
       </button>
    ```
    
- 표준화된 속성 정의
    - `id="listenBtn"`: 바로 듣기 버튼 식별자
    - `id="addCPList"`: 재생 목록 추가 버튼 식별자
    - `data-id`: 음원 고유 번호 저장 속성
- **도입 효과**
    - 팀원 간 일관된 인터페이스로 개발 효율성 향상
    - 페이지별 추가 작업 없이 즉시 재생 기능 연동
    - 유지보수성 향상 및 버그 발생 가능성 감소

> 이러한 표준화 작업을 통해 팀원들은 음악 재생 시스템과의 연동에 대한 부담 없이 각자의 페이지 개발에 집중할 수 있었으며, 결과적으로 프로젝트의 전반적인 개발 속도와 코드 품질 향상에 기여했습니다.
> 

## 3. 앨범 상세 페이지 구현

### 앨범 상세 페이지 - 아티스트 출력 기능 구현

음원 플랫폼의 앨범 상세 페이지에서 참여 아티스트를 출력하는 기능을 구현했습니다. 여러 아티스트와 그룹이 혼재된 데이터를 효율적으로 처리하여 사용자에게 직관적으로 표시하는 것이 핵심 과제였습니다.

**사용 기술**

- `Java` `HashMap` `Stream API` `MySQL`

**기술적 과제**

1. 복잡한 데이터 구조 처리
    - artists_roles 테이블에서 그룹/솔로 아티스트 구분
    - group_members 테이블의 그룹 멤버 정보 연동
    - 한 곡에 여러 아티스트가 참여한 경우 처리 필요
2. 비즈니스 요구사항
    - 그룹 참여 시 개별 멤버가 아닌 그룹명으로 표시
    - 앨범 내 참여 빈도수에 따른 정렬 출력

**문제 해결 과정**

두 가지 접근 방식을 검토

1. SQL 쿼리를 통한 데이터 처리
2. Java 로직을 통한 데이터 가공

Java 로직을 활용한 접근 방식을 선택한 이유:

- 비즈니스 로직의 유지보수성 향상
- 코드의 재사용성 확보
- 로직 변경에 대한 유연한 대응 가능

**구현 코드**

```java
	/**
	 * 앨범 정보 출력을 위한 참여 가수 가장 많이 등장한 가수를 먼저 보여줌.
	 */
	@Transactional(readOnly = true)
	public List<Artist> getSortedArtists(List<Song> songs, Integer roleId) {
		Map<Artist, Integer> artistCountMap = new HashMap<>();

		for (Song song : songs) {
			List<ArtistRole> artistRoles = artistRoleRepo.findBySongAndRoleCode_RoleId(song, roleId);
			for (ArtistRole artistRole : artistRoles) {
				Artist artist = artistRole.getArtist();
				artistCountMap.put(artist, artistCountMap.getOrDefault(artist, 0) + 1);
			}
		}

		// 가장 많이 등장한 가수를 먼저 보여주고, 횟수가 동일하면 이름 순으로 정렬함.
		return artistCountMap.entrySet().stream()
				.sorted(Map.Entry.<Artist, Integer>comparingByValue(Comparator.reverseOrder())
						.thenComparing(Map.Entry.comparingByKey(Comparator.comparing(Artist::getArtistName))))
				.map(Map.Entry::getKey).collect(Collectors.toList());
	}
```

**결과 및 성과**

- 복잡한 데이터 구조를 효율적으로 처리하여 사용자 친화적인 출력 구현
- Map 자료구조를 활용한 확장 가능한 설계로 향후 기능 추가 용이
- 비즈니스 로직 분리를 통한 코드 유지보수성 향상

### 구현 결과 스크린샷

![4_구현결과](https://github.com/user-attachments/assets/3b327bc4-955f-468a-a944-1a644fe99594)


> 이 경험을 통해 복잡한 비즈니스 요구사항을 효율적으로 처리하는 방법과 확장 가능한 설계의 중요성을 배웠습니다. 특히 자료구조의 적절한 활용이 복잡한 로직 처리에 큰 도움이 된다는 것을 깨달았습니다.
> 

## 4. 검색 쿼리 최적화

### 기술 스택

`MySQL Workbench` `Visual Explain`

### 목표

- MySQL 프로시저를 이용해 100만 건의 더미 데이터를 삽입한 후, 검색 기능이 원활하게 동작하도록 최적화

### 문제점

- **초기 쿼리:** 초기 팀원이 작성한 검색 쿼리는 로직의 정확성에 초점을 맞추어 설계되었으나, 대량 데이터 처리 시 성능 저하 문제가 발생
    - **초기 쿼리 실행 시간: 16.656 sec**
    
    ![5_쿼리실행시간](https://github.com/user-attachments/assets/5a07d8c5-ccdc-42da-b784-7351c46fcb11)

    

### 문제 분석

**Visual Explain 분석**

![6_Visual Explain](https://github.com/user-attachments/assets/933ec246-cd6c-4a10-9691-8726ecd5b25e)


- **높은 QueryCost:**
    - Join 횟수에 따라 QueryCost가 약 200만으로 매우 높게 책정되어 있었음.
    - 필요 없는 Join을 제거하고, 최적화를 통해 성능 개선이 필요.
- **최적화 대상 쿼리:**
    
    ```sql
    SELECT 
        a.album_id, 
        a.album_name, 
        a.album_image, 
        a.album_type, 
        a.album_release_date, 
        mp.participant_name AS artist_name, 
        mp.participant_id AS artist_id, 
        mp.participant_type AS artist_type, 
        COUNT(al.album_id) AS like_count 
    FROM albums a 
    LEFT JOIN ( 
        SELECT  
            tmp.album_id, 
            tmp.participant_name, 
            tmp.participant_id, 
            tmp.participant_type, 
            tmp.participation_count 
        FROM ( 
            SELECT  
                s.album_id, 
                COALESCE(g.group_name, art.artist_name) AS participant_name, 
                COALESCE(g.group_id, art.artist_id) AS participant_id, 
                CASE  
                    WHEN g.group_id IS NOT NULL THEN 'group' 
                    ELSE 'artist' 
                END AS participant_type, 
                COUNT(*) as participation_count, 
                ROW_NUMBER() OVER (PARTITION BY s.album_id ORDER BY COUNT(*) DESC) as rn 
            FROM songs s 
            JOIN artist_roles ar ON s.song_id = ar.song_id 
            LEFT JOIN artists art ON ar.artist_id = art.artist_id 
            LEFT JOIN `groups` g ON ar.group_id = g.group_id 
            GROUP BY s.album_id, g.group_name, art.artist_name, g.group_id, art.artist_id
        ) AS tmp 
        WHERE tmp.rn = 1 
    ) mp ON a.album_id = mp.album_id 
    left join album_likes al on a.album_id = al.album_id
    WHERE a.album_name LIKE CONCAT('%', '검색어', '%')
    GROUP BY a.album_id, a.album_name, a.album_image, a.album_type, a.album_release_date,  
             mp.participant_name, mp.participant_id, mp.participant_type 
    ORDER BY a.album_name, like_count DESC 
    LIMIT 18 OFFSET 0;
    ```
    
- **문제 원인:**
    - **표시된 서브쿼리**가 모든 데이터에 대해 실행됨
    - **총 2,000,000번의 Join**으로 인해 QueryCost가 2,000,000으로 책정됨
    - **데이터 개수:**
        - albums 67,000, songs 670,000, groups 67,000, artists 200,000, artist_roles 1,000,000
        - 약 200만 개

**해결 방법:**

- **서브쿼리의 조건을 최적화하여 불필요한 Join을 제거.**
    - 적절한 **Join 조건** 선택:
    - `WHERE a.album_name LIKE CONCAT('%', '검색어', '%')`
    - **‘검색어’를 포함**하는 데이터에 한 해서 만 **서브쿼리를 실행**
- **EXISTS 함수를 이용한 최적화:**
    
    ```sql
    		   ...
    	    
        FROM ( 
    			        SELECT
    			            s.album_id,
    			            COALESCE(g.group_name, art.artist_name) AS participant_name,
    			            COALESCE(g.group_id, art.artist_id) AS participant_id,
    			            CASE
    			                WHEN g.group_id IS NOT NULL THEN 'group'
    			                ELSE 'artist'
    			            END AS participant_type,
    			            COUNT(*) as participation_count,
    			            ROW_NUMBER() OVER (PARTITION BY s.album_id ORDER BY COUNT(*) DESC) as rn
    			        FROM songs s
    			        JOIN artist_roles ar ON s.song_id = ar.song_id
    							AND ar.role_id = 10
    			        LEFT JOIN artists art ON ar.artist_id = art.artist_id
    			        LEFT JOIN `groups` g ON ar.group_id = g.group_id
    			        WHERE EXISTS (
    			            SELECT 1
    			            FROM albums a_inner
    			            WHERE a_inner.album_id = s.album_id
    			              AND a_inner.album_name LIKE CONCAT('%', '검색어', '%')
    			        ) 
    			        GROUP BY s.album_id, participant_name, participant_id, participant_type
    			    ) AS tmp
    			        
         ...
    ```
    
- **최적화 적용 후 성능**
    - **쿼리 실행 시간: 0.078 sec**
    
    ![7_쿼리실행시간](https://github.com/user-attachments/assets/40194c56-d72e-4e7e-913d-8b1619a3f29f)

    
    - **MySQL Visual Explain**
    
    
    ![8_VisualExplain](https://github.com/user-attachments/assets/7e678013-52ba-46e2-9021-d7458446813f)


### 결과

- **QueryCost:** 2,087,316 ⇒ 32,780 (98% 이상 감소)
- **쿼리 실행 시간:** 16.656초 ⇒ 0.078초 (99% 이상 단축)

## 5. 초성 검색 로직 구현

음악 검색 서비스의 한글 초성 검색 기능 설계 및 구현

### 사용 기술

- `Spring Boot JPA` `Native Query` `MySQL`

### **주요 구현 내용**

1. 초성 데이터 설계
    - 검색 대상(앨범명, 음원명, 그룹명, 아티스트명)별 초성 정보를 저장하는 테이블 설계
    - 초성 추출 알고리즘 구현
        - 유니코드 매핑을 통한 한글 초성 추출
        - 영문/숫자 등 비한글 문자 처리 로직 포함
    - 사용 초성 변환 로직
        
        ```java
        	// 초성 배열
        	private static final char[] CHOSUNG = {
        			'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
        	};
        
        	// 문자가 한글인 지 검사하는 메서드
        	public static boolean isHangul(char c) {
        		return c >= '\uAC00' && c <= '\uD7A3';
        	}
        
        	public static String getInitialSound(String str) {
        		StringBuilder sb = new StringBuilder();
        		for (char ch : str.toCharArray()) {
        			if (isHangul(ch)) {
        				int unicode = ch - 0xAC00;
        				int chosungIndex = unicode / (21 * 28); // 초성 계산
        				sb.append(CHOSUNG[chosungIndex]);
        			} else {
        				// 한글이 아닌 경우 그대로 출력
        				sb.append(ch);
        			}
        		}
        		return sb.toString();
        	}
        ```
        
2. 검색 로직 구현
    - JPA Native Query를 활용한 초성 기반 검색 쿼리 작성
    - 다중 테이블(앨범, 음원, 그룹, 아티스트) 검색 결과 통합 반환
    - 사용 Native Query
        
        ```java
        	@Query(value = """
        			            SELECT DISTINCT
        			    'album' AS type,
        			    a.album_id AS id,
        			    a.album_name AS name,
        			    a.album_release_date AS release_date,
        			    (SELECT COALESCE(ls.like_count, 0)
        			     FROM likes_statistics ls
        			     WHERE ls.type = 'album'
        			       AND ls.item_id = a.album_id
        			    ) AS like_count
        			FROM albums a
        			JOIN albums_first_con afc ON a.album_id = afc.album_id
        			WHERE afc.album_first_con_name LIKE CONCAT('%', :searchTerm, '%')
        
        			UNION ALL
        
        			SELECT DISTINCT
        			    'song' AS type,
        			    s.song_id AS id,
        			    s.title AS name,
        			    NULL AS release_date,
        			    (SELECT COALESCE(ls.like_count, 0)
        			     FROM likes_statistics ls
        			     WHERE ls.type = 'song'
        			       AND ls.item_id = s.song_id
        			    ) AS like_count
        			FROM songs s
        			JOIN songs_first_con sfc ON s.song_id = sfc.song_id
        			WHERE sfc.song_first_con_name LIKE CONCAT( :searchTerm, '%')
        
        			UNION ALL
        
        			SELECT DISTINCT
        			    'group' AS type,
        			    g.group_id AS id,
        			    g.group_name AS name,
        			    NULL AS release_date,
        			    (SELECT COALESCE(ls.like_count, 0)
        			     FROM likes_statistics ls
        			     WHERE ls.type = 'group'
        			       AND ls.item_id = g.group_id
        			    ) AS like_count
        			FROM `groups` g
        			JOIN groups_first_con gfc ON g.group_id = gfc.group_id
        			WHERE gfc.group_first_con_name LIKE CONCAT('%', :searchTerm, '%')
        
        			UNION ALL
        
        			SELECT DISTINCT
        			    'artist' AS type,
        			    a.artist_id AS id,
        			    a.artist_name AS name,
        			    NULL AS release_date,
        			    (SELECT COALESCE(ls.like_count, 0)
        			     FROM likes_statistics ls
        			     WHERE ls.type = 'artist'
        			       AND ls.item_id = a.artist_id
        			    ) AS like_count
        			FROM artists a
        			JOIN artists_first_con afc ON a.artist_id = afc.artist_id
        			WHERE afc.artist_first_con_name LIKE CONCAT('%', :searchTerm, '%')
        
        			ORDER BY
        			    LENGTH(name),
        			    like_count DESC
        			    """
        			+ "LIMIT 5"
        
        			            , nativeQuery = true)
        	List<Object[]> findChoSeongBySearchTerm(@Param("searchTerm") String searchTerm);
        ```
        

### 결과

- 사용자가 초성만으로도 원하는 음악 콘텐츠를 검색 가능
- 한글 초성 변환 알고리즘의 안정적 구현

### 위 로직을 이용해 팀원이 구현

![9_초성 구현](https://github.com/user-attachments/assets/a7e43a44-f3a3-4187-870b-bc08f3609ef8)


# **프로젝트를 통해 배운 점**

우리 팀은 프로젝트 진행 중 여러 기술적, 협업적 도전과제를 마주했고, 이를 통해 많은 것을 배웠습니다.

### **깃허브 협업과 버전 관리**

깃허브를 통한 협업 과정에서 merge 충돌은 처음에는 큰 어려움이었지만, 점차 브랜치 전략과 효과적인 커뮤니케이션의 중요성을 깨달았습니다. 충돌을 최소화하기 위해 작은 단위의 커밋, 명확한 커밋 메시지, 그리고 정기적인 코드 리뷰의 중요성을 배웠습니다. 

### **데이터베이스 설계의 복잡성**

N:N 관계 처리는 초기에 큰 고민거리였습니다. 중간 테이블을 활용하여 관계를 효과적으로 모델링하는 방법을 배웠고, 이를 통해 관계형 데이터베이스의 설계 원칙을 더 깊이 이해할 수 있었습니다. ERD를 통해 데이터 구조를 시각화하고 논리적으로 접근하는 방법도 익혔습니다.

### **멀티미디어 상태 관리**

`<frame>` 태그로 페이지 이동 시 재생 상태를 유지할 수 있었지만, 재생 목록 관리에서 여러 어려움을 겪었습니다. 배열로 관리하는 재생 목록과 현재 곡 인덱스가 실제 UI와 동기화되지 않는 문제가 있었고, 변수 변경 시 애플리케이션에 즉각 반영되지 않았습니다.

 이러한 복잡한 상태 관리를 위해 매우 복잡한 JavaScript 코드를 작성해야 했고, 팀원들에게 설명하는 데 어려움을 겪었습니다. 이 경험을 통해 상태 관리 라이브러리의 필요성을 깊이 이해하게 되었습니다.

### **데이터베이스 성능 최적화**

100만 건의 더미데이터를 다루면서 쿼리 최적화의 중요성을 몸소 체험했습니다. 인덱스 생성, 쿼리 실행 계획 분석, 불필요한 조인 제거 등의 기법을 통해 쿼리 성능을 크게 향상시킬 수 있었습니다. 이 과정에서 데이터베이스의 내부 작동 방식과 성능 튜닝에 대한 깊은 이해를 얻었습니다.

## **결론**

이번 프로젝트는 단순한 기술 구현을 넘어 실제 개발 현장에서 마주하는 도전과제들을 경험할 수 있는 귀중한 기회였습니다. 기술적 성장뿐만 아니라 팀원들과의 협업, 문제 해결 능력, 그리고 지속적인 학습의 중요성을 깨달았습니다. 앞으로도 이러한 경험들을 바탕으로 끊임없이 성장하는 개발자가 되겠습니다.
