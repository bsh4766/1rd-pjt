import { useOutletContext } from "react-router-dom";
import Spinner from "../../common/Spinner";
import BracketButton from "../../Button/BracketButton";
import Sticker from "../../../assets/images/sticker.png";
import ResultModal from "../../common/ResultModal";
import { useState } from "react";
import JihwaProgressBar from "./JihwaProgressBar";
import { trainingDataType } from "../Lecture";

const JihwaComponent = () => {
  const [modalShown, setModalShown] = useState<boolean>(false);
  const [success, setSuccess] = useState<boolean>(false);
  interface IFollowersContext {
    followStatus: Boolean;
    trainingData: trainingDataType[];
    setTrainingData: (trainingData: trainingDataType) => void;
    currentNum: number;
    currentNumModify: (currentNum: number) => void;
    addSticker: number;
  }
  const { followStatus, trainingData, currentNum, currentNumModify, addSticker } =
    useOutletContext<IFollowersContext>();

  const successModal = () => {
    setModalShown(!modalShown);
    setSuccess(modalShown ? false : true);
  };
  const failModal = () => {
    setModalShown(!modalShown);
    setSuccess(false);
  };

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        style={{
          width: "1080px",
          height: "200px",
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <JihwaProgressBar
          trainingData={trainingData}
          currentNum={currentNum}
          currentNumModify={currentNumModify}
        />
      </div>

      {followStatus ? (
        <>
          <div
            style={{
              display: "flex",
              flexDirection: "row",
              justifyContent: "space-around",
              alignItems: "center",
              width: "1080px",
              height: "510px",
            }}
          >
            <div
              style={{
                width: "533px",
                height: "510px",
                borderRadius: "40px",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                fontSize: "40px",
                fontWeight: "bold",
                border: "1px solid black",
              }}
            >
              지화 모델
            </div>
            <img
              style={{
                width: "533px",
                height: "510px",
                borderRadius: "40px",
              }}
              src={trainingData[currentNum - 1].imagePath}
              alt="jihwa"
            />
          </div>
          <div
            style={{
              height: "0px",
              position: "relative",
              top: "90px",
              left: "70px",
              display: "flex",
              flexDirection: "row",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Spinner />
            <span
              style={{
                position: "relative",
                left: "350px",
                display: "flex",
                flexDirection: "row",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <img src={Sticker} alt="sticker" />
              <span
                style={{
                  fontSize: "40px",
                  fontWeight: "bold",
                  marginLeft: "10px",
                }}
              >
                X {addSticker}
              </span>
              <div>
                <div>
                  <button onClick={successModal}>성공 모달 테스트</button>
                </div>
                <button onClick={failModal}>실패 모달 테스트</button>
              </div>
            </span>
          </div>
          <ResultModal
            success={success}
            setSuccess={setSuccess}
            shown={modalShown}
            setModalShown={setModalShown}
            BookmarkButton={true}
            stickerNum={addSticker}
            currentNum={currentNum}
            currentNumModify={currentNumModify}
            totalNum={trainingData.length}
            signId={trainingData[currentNum - 1].signId}
          />
        </>
      ) : (
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-around",
            alignItems: "center",
            width: "1080px",
            height: "510px",
          }}
        >
          {currentNum === 1 ? (
            <div
              style={{
                width: "80px",
                height: "116px",
              }}
            />
          ) : (
            <BracketButton
              direction="left"
              currentNum={currentNum}
              currentNumModify={currentNumModify}
            />
          )}
          <img
            style={{
              width: "907px",
              height: "510px",
              borderRadius: "40px",
            }}
            src={trainingData[currentNum - 1].imagePath}
            alt="jihwa"
          />
          {currentNum === trainingData.length ? (
            <div
              style={{
                width: "80px",
                height: "116px",
              }}
            />
          ) : (
            <BracketButton
              direction="right"
              currentNum={currentNum}
              currentNumModify={currentNumModify}
            />
          )}
        </div>
      )}
    </div>
  );
};

export default JihwaComponent;
