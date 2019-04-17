
magtobmp	: MagToBmp.o BitmapAndPixelCount.o MagFile.o
	g++ MagToBmp.o BitmapAndPixelCount.o MagFile.o -o magtobmp

MagToBmp.o	: MagToBmp.cpp BitmapAndPixelCount.o MagFile.o
	g++ MagToBmp.cpp -c -Wall

BitmapAndPixelCount.o	: BitmapAndPixelCount.cpp BitmapAndPixelCount.h
	g++ BitmapAndPixelCount.cpp -c -Wall

MagFile.o	: MagFile.cpp MagFile.h
	g++ MagFile.cpp -c -Wall

clean	:
	rm -fr *.o
