package com.notx2wice.puzzlemakers.repository.dynamo.data;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "puzzler")
public class Puzzler {
    @DynamoDBHashKey
    String googleId;

    @DynamoDBAttribute
    String nickName; //닉네임 변환에 캐쉬

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.SS)
    @DynamoDBAttribute
    Set<String> solvedQuizs = new HashSet<>(); //푼 문제들

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.SS)
    @DynamoDBAttribute //동시 3개 까지 풀 수 있음.
    Set<String> solvingQuizs = new HashSet<>();

    @DynamoDBAttribute // 현재 풀고있는 오픈된 퀴즈 갯수
    int solvingCount;

    @DynamoDBAttribute
    int hintCount;

    @DynamoDBAttribute
    int maxQuizCount;

    @DynamoDBAttribute
    int score;

    @DynamoDBAttribute
    int cash;

    @DynamoDBAttribute(attributeName = "adFree")
    boolean isAdFree;
}
