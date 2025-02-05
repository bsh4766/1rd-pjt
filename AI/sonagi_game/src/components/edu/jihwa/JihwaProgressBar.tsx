import { styled } from "styled-components";
import { trainingDataType } from "../Lecture";

const Span = styled.span`
  width: 80px;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0px -20px;
  cursor: pointer;
`;

interface JihwaProgressBarProps {
  trainingData: {
    signId: number;
    content: string;
    imagePath: string;
    videoPath: string;
  }[];
  currentNum: number;
  currentNumModify: (currentNum: number) => void;
}

const JihwaProgressBar = ({
  trainingData,
  currentNum,
  currentNumModify,
}: JihwaProgressBarProps) => {
  const trainingDataLength = trainingData.length;
  const onClick = (e: React.MouseEvent<HTMLInputElement>) => {
    const idNumber = parseInt((e.target as HTMLInputElement).id);
    if (idNumber < trainingDataLength + 1 && idNumber > 0) currentNumModify(idNumber);
  };

  return (
    <>
      <div>
        <span>
          <span
            style={{
              fontSize: "35px",
              fontWeight: "bold",
              color: "red",
            }}
          >
            {currentNum}
          </span>
          <span
            style={{
              fontSize: "28px",
              fontWeight: "bold",
            }}
          >
            /{trainingDataLength}
          </span>
        </span>
      </div>
      <div
        style={{
          width: "793px",
          height: "136px",
          borderRadius: "70px",
          padding: "0px 40px",
          backgroundColor: "#D9D9D9",
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-around",
          alignItems: "center",
          fontSize: "50px",
          fontWeight: "bold",
          color: "#636363",
          // 폰트 밝기 조절
        }}
      >
        <Span id={(currentNum - 3).toString()} onClick={onClick}>
          {((currentNum: number) => {
            if (currentNum < 4) {
              return "";
            }
            return trainingData[currentNum - 4].content;
          })(currentNum)}
        </Span>
        <Span id={(currentNum - 2).toString()} onClick={onClick}>
          {((currentNum: number) => {
            if (currentNum < 3) {
              return "";
            }
            return trainingData[currentNum - 3].content;
          })(currentNum)}
        </Span>
        <Span id={(currentNum - 1).toString()} onClick={onClick}>
          {((currentNum: number) => {
            if (currentNum < 2) {
              return "";
            }
            return trainingData[currentNum - 2].content;
          })(currentNum)}
        </Span>
        <span
          style={{
            width: "180px",
            height: "108px",
            borderRadius: "30px",
            backgroundColor: "#C2FFF0",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            color: "black",
            fontSize: "80px",
            margin: "0px -15px",
          }}
        >
          <span
            style={{
              position: "relative",
              top: "-5px",
            }}
          >
            {trainingData[currentNum - 1].content}
          </span>
        </span>
        <Span id={(currentNum + 1).toString()} onClick={onClick}>
          {((currentNum: number) => {
            if (currentNum > trainingDataLength - 1) {
              return "";
            }
            return trainingData[currentNum].content;
          })(currentNum)}
        </Span>
        <Span id={(currentNum + 2).toString()} onClick={onClick}>
          {((currentNum: number) => {
            if (currentNum > trainingDataLength - 2) {
              return "";
            }
            return trainingData[currentNum + 1].content;
          })(currentNum)}
        </Span>
        <Span id={(currentNum + 3).toString()} onClick={onClick}>
          {((currentNum: number) => {
            if (currentNum > trainingDataLength - 3) {
              return "";
            }
            return trainingData[currentNum + 2].content;
          })(currentNum)}
        </Span>
      </div>
    </>
  );
};

export default JihwaProgressBar;
