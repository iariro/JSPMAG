#include <stdio.h>
#include <sys/stat.h>
#include "BitmapAndPixelCount.h"
#include "MagFile.h"

int main(int argc, char * argv [])
{
	if (argc < 2)
	{
		puts("Usage: magtobmp filename");
		return 0;
	}

	struct stat st;
	if (stat(argv[1], &st) == 0)
	{
		// MAG画像の読み込み
		unsigned char * bytes = new unsigned char [st.st_size];

		MagFile magFile(bytes);

		unsigned char ** palette = magFile.getPalette();
		BitmapAndPixelCount bitmap = magFile.getBitmap();

		delete [] bytes;
	}

	return 0;
}
