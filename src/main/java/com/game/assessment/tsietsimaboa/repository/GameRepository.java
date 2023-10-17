package com.game.assessment.tsietsimaboa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.assessment.tsietsimaboa.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
