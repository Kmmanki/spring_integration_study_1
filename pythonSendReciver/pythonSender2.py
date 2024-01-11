import tkinter as tk
from tkinter import messagebox
import pyaudio
import wave

recFlag=True

def on_start_button_click():
    p = pyaudio.PyAudio()
    filename="./sample.wav"
    duration=5
    sample_rate=16000
    channels=1
    format=pyaudio.paInt16
    try:
        stream = p.open(format=format,
                        channels=channels,
                        rate=sample_rate,
                        input=True,
                        frames_per_buffer=1024)


        frames = []

        for i in range(0, int(sample_rate / 1024 * duration)):
            try:
                data = stream.read(1024)
                frames.append(data)
            except KeyboardInterrupt:
                break

        print("녹음이 완료되었습니다.")

        stream.stop_stream()
        stream.close()
        p.terminate()

        with wave.open(filename, 'wb') as wf:
            wf.setnchannels(channels)
            wf.setsampwidth(p.get_sample_size(format))
            wf.setframerate(sample_rate)
            wf.writeframes(b''.join(frames))

        print(f"녹음된 오디오가 {filename}로 저장되었습니다.")
    except Exception as e:
        print(f"에러 발생: {e}")


def on_exit_button_click():
    global recFlag
    recFlag = False
# Tkinter 윈도우 생성
root = tk.Tk()
root.title("간단한 GUI 예제")
root.geometry("300x200")  # 창의 크기 설정

# 시작 버튼 생성 및 배치
start_button = tk.Button(root, text="시작", command=on_start_button_click)
start_button.pack(side="top", pady=10)


# 종료 버튼 생성 및 배치
exit_button = tk.Button(root, text="종료", command=on_exit_button_click)
exit_button.pack(side="top", pady=10)


# 시작과 종료 버튼을 정중앙에 배치
root.grid_rowconfigure(0, weight=1)
root.grid_columnconfigure(0, weight=1)

# Tkinter 이벤트 루프 시작
root.mainloop()
