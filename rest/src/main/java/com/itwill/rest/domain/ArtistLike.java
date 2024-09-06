package com.itwill.rest.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "ARTIST_LIKES")
@Getter @NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder @ToString @EqualsAndHashCode
public class ArtistLike {
	
	@EmbeddedId
	private ArtistLikeId artistLikeId;

}
