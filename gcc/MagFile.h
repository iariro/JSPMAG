
/**
 * MAGファイル
 * @author kumagai
 */
class MagFile
{
private:
	unsigned char * bytes;

public:
	int screenMode;
	int width;
	int height;
private:
	int memoLength;
	long startFlagA;
	long startFlagB;
	long startPixel;
public:
	long sizePixel;

public:
	MagFile(unsigned char * bytes);
	int getFlagALength();
	int getFlagBLength();
	char * getMemo();
	unsigned char ** getPalette();
	BitmapAndPixelCount getBitmap();
};
