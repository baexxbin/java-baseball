package baseball;

import static baseball.GameRules.*;

import java.util.List;

import camp.nextstep.edu.missionutils.Console;

public class BaseballGameController {
	private UserPlayer userPlayer;
	private BaseballGameModel baseballGameModel;
	private boolean gameStatus;
	private final UserInputValidator userInputValidator;

	public BaseballGameController(UserPlayer userPlayer, BaseballGameModel baseballGameModel, UserInputValidator userInputValidator) {
		this.userPlayer = userPlayer;
		this.baseballGameModel = baseballGameModel;
		this.userInputValidator = userInputValidator;
		System.out.println("숫자 야구 게임을 시작합니다.");
	}

	public void startGame() {
		gameInit();
		while (gameStatus){
			playGame();
			choiceGameProgress();
		}
	}

	public void playGame() {
		String userInput;
		List<Integer> scoreList;

		while (gameStatus) {
			System.out.print("숫자를 입력해주세요 : ");
			userInput = Console.readLine();

			userInputValidator.isValidUserInput(userInput);
			userPlayer.setNumberList(userInput);

			scoreList = baseballGameModel.getMatchResult();

			if (baseballGameModel.isGameOver()) {
				System.out.println(DIGIT_NUMBER.getCode()+"스트라이크");
				System.out.println(DIGIT_NUMBER.getCode()+"개의 숫자를 모두 맞히셨습니다! 게임 종료");
				break;
			}
			printMatchResult(scoreList);
		}
	}

	public void gameInit() {
		ComputerPlayer computerPlayer = new ComputerPlayer();
		userPlayer = UserPlayer.getInstance();
		baseballGameModel = new BaseballGameModel(computerPlayer);
		gameStatus = true;
	}

	public void choiceGameProgress() {
		System.out.println("게임을 새로 시작하려면 "+ GAME_RESTART.getCode()+ ", 종료하려면 "+GAME_END.getCode()+ "를 입력하세요.");
		String gameProgressNumber = Console.readLine();

		userInputValidator.isValidProgressNumber(gameProgressNumber);

		if (Integer.parseInt(gameProgressNumber) == GAME_END.getCode()) {
			gameStatus = false;
			System.out.println("게임을 진짜 종료합니다.");
			return;
		}

		System.out.println("새로운 게임을 시작합니다.");
		gameInit();
	}

	public void printMatchResult(List<Integer> scoreList) {
		if (scoreList.isEmpty()) {
			System.out.println("낫싱");
			return;
		}

		for (int i = 0; i < scoreList.size(); i++) {
			if (scoreList.get(i) == 0) {
				continue;
			}
			if (i == 0) {
				System.out.print(scoreList.get(i)+"볼 ");
				continue;
			}
			System.out.println(scoreList.get(i)+"스트라이크");
		}
		System.out.println();
	}

}
