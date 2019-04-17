
/**
 * ビットマップ部とピクセル参照カウント
 * @author kumagai
 */
class BitmapAndPixelCount
{
public:
	unsigned char ** bitmap;
	int pixelCount;
	int referCount;

public:
	BitmapAndPixelCount(unsigned char ** bitmap, int pixelCount, int referCount);
	float getPixelCountRatio();
};
