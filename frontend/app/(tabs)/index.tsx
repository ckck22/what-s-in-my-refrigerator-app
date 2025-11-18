import { useState, useRef } from 'react';
import { StyleSheet, Text, View, Button, Image, ActivityIndicator, ScrollView, SafeAreaView, TouchableOpacity } from 'react-native';
import { CameraView, useCameraPermissions, CameraCapturedPicture } from 'expo-camera';
import axios from 'axios';

// 1. Quiz Data Structure (Backend Response)
interface Translation {
  languageCode: string;
  translatedWord: string;
}

interface QuizItem {
  labelEn: string;
  nameKo: string;
  translations: Translation[];
}

// 2. Define types for 'any' related errors
interface AxiosErrorResponse {
  response?: {
    data?: string;
  };
  message: string;
}

export default function App() {
  // Camera permissions
  const [permission, requestPermission] = useCameraPermissions();

  // State for camera reference
  const cameraRef = useRef<CameraView>(null);

  // State for the captured photo
  const [photo, setPhoto] = useState<CameraCapturedPicture | null>(null);

  // State for loading status
  const [loading, setLoading] = useState(false);

  // State for quiz data received from the backend
  const [quizData, setQuizData] = useState<QuizItem[] | null>(null);

  // 1. Handle Permissions
  if (!permission) {
    // Camera permissions are still loading
    return <View />;
  }
  if (!permission.granted) {
    // Camera permissions are not granted yet
    return (
      <View style={styles.container}>
        <Text style={{ textAlign: 'center' }}>We need your permission to show the camera</Text>
        <Button onPress={requestPermission} title="grant permission" />
      </View>
    );
  }

  // 2. Function to take a picture
  const takePicture = async () => {
    if (cameraRef.current) {
      try {
        const data = await cameraRef.current.takePictureAsync({
          quality: 0.5, // Compress image to reduce upload time
          base64: false,
        });
        setPhoto(data);
      } catch (error) {
        console.error("Failed to take picture:", error);
      }
    }
  };

  // 3. Function to upload image to Spring Boot backend
  const generateQuiz = async () => {
    if (!photo) return;

    setLoading(true);

    // Prepare the form data
    const formData = new FormData();
    formData.append('image', {
      uri: photo.uri,
      name: 'fridge_photo.jpg',
      type: 'image/jpeg',
    } as any);

    try {
      // Replace 'localhost' with your computer's IP address if testing on a real device.
      // For iOS Simulator, 'localhost' usually works. 
      // For Android Emulator, use '10.0.2.2'.
      const BACKEND_URL = 'http://192.168.86.25:8080/api/quiz/generate';
      
      console.log("Sending request to:", BACKEND_URL);

      const response = await axios.post(BACKEND_URL, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      console.log("Response received:", response.data);
      setQuizData(response.data);

    } catch (err) { 
      const error = err as AxiosErrorResponse; 
      console.error("Error generating quiz:", error);
      alert("Error: " + (error.response?.data || error.message));
    } finally {
      setLoading(false);
    }
  };

  // 4. Reset to camera view
  const reset = () => {
    setPhoto(null);
    setQuizData(null);
  };

  // --- RENDER ---

  // If we have quiz data, show the Quiz Screen
  if (quizData) {
    return (
      <SafeAreaView style={styles.container}>
        <ScrollView contentContainerStyle={styles.resultContainer}>
          <Text style={styles.header}>✨ Your Fridge Quiz ✨</Text>
          {quizData.map((item, index) => (
            <View key={index} style={styles.card}>
              <Text style={styles.labelEn}>{item.labelEn}</Text>
              <Text style={styles.nameKo}>({item.nameKo})</Text>
              <View style={styles.separator} />
              {item.translations.map((t, tIndex) => (
                <Text key={tIndex} style={styles.translation}>
                  {t.languageCode.toUpperCase()}: {t.translatedWord}
                </Text>
              ))}
            </View>
          ))}
          <Button title="Scan Again" onPress={reset} />
        </ScrollView>
      </SafeAreaView>
    );
  }

  // If photo is taken but not uploaded yet (Preview Screen)
  if (photo) {
    return (
      <View style={styles.container}>
        <Image source={{ uri: photo.uri }} style={styles.preview} />
        <View style={styles.controls}>
          {loading ? (
            <ActivityIndicator size="large" color="#0000ff" />
          ) : (
            <>
              <Button title="Retake" onPress={() => setPhoto(null)} />
              <Button title="Generate Quiz" onPress={generateQuiz} />
            </>
          )}
        </View>
      </View>
    );
  }

  // Default: Camera Screen
  return (
    <View style={styles.container}>
      <CameraView style={styles.camera} ref={cameraRef}>
        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.button} onPress={takePicture}>
            <Text style={styles.text}>📸 SNAP</Text>
          </TouchableOpacity>
        </View>
      </CameraView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#fff' },
  camera: { flex: 1 },
  buttonContainer: { flex: 1, flexDirection: 'row', backgroundColor: 'transparent', margin: 64 },
  button: { flex: 1, alignSelf: 'flex-end', alignItems: 'center', backgroundColor: 'white', padding: 15, borderRadius: 10 },
  text: { fontSize: 24, fontWeight: 'bold', color: 'black' },
  preview: { flex: 1 },
  controls: { flexDirection: 'row', justifyContent: 'space-around', padding: 20 },
  resultContainer: { padding: 20 },
  header: { fontSize: 28, fontWeight: 'bold', marginBottom: 20, textAlign: 'center' },
  card: { backgroundColor: '#f9f9f9', padding: 15, borderRadius: 10, marginBottom: 15, shadowColor: '#000', shadowOpacity: 0.1, shadowRadius: 5, elevation: 3 },
  labelEn: { fontSize: 22, fontWeight: 'bold', color: '#333' },
  nameKo: { fontSize: 16, color: '#666', marginBottom: 10 },
  separator: { height: 1, backgroundColor: '#eee', marginVertical: 5 },
  translation: { fontSize: 18, color: '#007AFF' },
});