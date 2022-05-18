import React, { useEffect, useState } from "react";
import SpeechRecognition, {
  useSpeechRecognition,
} from "react-speech-recognition";

function SpeechToText() {
  const { transcript, resetTranscript } = useSpeechRecognition();
  useEffect(() => {
    SpeechRecognition.startListening({continuous: true});
    console.log('listening start')
  }, []);
  return <div>SpeechToText</div>;
}

export default SpeechToText;