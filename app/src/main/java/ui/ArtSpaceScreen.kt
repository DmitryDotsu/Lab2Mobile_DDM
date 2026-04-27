package com.example.lab2mobile_ddm.ui

// импорты
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab2mobile_ddm.R
import com.example.lab2mobile_ddm.data.Artwork

@Composable
fun ArtSpaceScreen() {
    // Состояние с rememberSaveable для сохранения при повороте
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }

    // Получаем список произведений искусства
    val artworks = remember { getArtworks() }
    val currentArtwork = artworks[currentIndex]

    // Определяем состояние кнопок
    val isPreviousEnabled = currentIndex > 0
    val isNextEnabled = currentIndex < artworks.size - 1

    // Получаем ориентацию экрана
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    // Основной макет с учетом ориентации
    if (isLandscape) {
        LandscapeLayout(
            artwork = currentArtwork,
            isPreviousEnabled = isPreviousEnabled,
            isNextEnabled = isNextEnabled,
            onPreviousClick = { if (isPreviousEnabled) currentIndex-- },
            onNextClick = { if (isNextEnabled) currentIndex++ }
        )
    } else {
        PortraitLayout(
            artwork = currentArtwork,
            isPreviousEnabled = isPreviousEnabled,
            isNextEnabled = isNextEnabled,
            onPreviousClick = { if (isPreviousEnabled) currentIndex-- },
            onNextClick = { if (isNextEnabled) currentIndex++ }
        )
    }
}

@Composable
fun PortraitLayout(
    artwork: Artwork,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Изображение (занимает ~60% экрана)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Image(
                painter = painterResource(id = artwork.imageResId),
                contentDescription = artwork.contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics {
                        this.contentDescription = artwork.contentDescription
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Информация о произведении
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = artwork.titleResId),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(id = artwork.artistResId),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = stringResource(id = artwork.yearResId),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопки навигации
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onPreviousClick,
                enabled = isPreviousEnabled,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .semantics {
                        this.contentDescription = "Предыдущее произведение"
                    }
            ) {
                Text(stringResource(id = R.string.previous_button))
            }

            Button(
                onClick = onNextClick,
                enabled = isNextEnabled,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .semantics {
                        this.contentDescription = "Следующее произведение"
                    }
            ) {
                Text(stringResource(id = R.string.next_button))
            }
        }
    }
}

@Composable
fun LandscapeLayout(
    artwork: Artwork,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Левая колонка - изображение
        Card(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight(0.9f),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Image(
                painter = painterResource(id = artwork.imageResId),
                contentDescription = artwork.contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics {
                        this.contentDescription = artwork.contentDescription
                    }
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Правая колонка - информация и кнопки
        Column(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Информация
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = artwork.titleResId),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(id = artwork.artistResId),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = stringResource(id = artwork.yearResId),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопки
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onPreviousClick,
                    enabled = isPreviousEnabled,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .semantics {
                            this.contentDescription = "Предыдущее произведение"
                        }
                ) {
                    Text(stringResource(id = R.string.previous_button))
                }

                Button(
                    onClick = onNextClick,
                    enabled = isNextEnabled,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .semantics {
                            this.contentDescription = "Следующее произведение"
                        }
                ) {
                    Text(stringResource(id = R.string.next_button))
                }
            }
        }
    }
}

// Функция для получения коллекции произведений искусства
fun getArtworks(): List<Artwork> {
    return listOf(
        Artwork(
            imageResId = R.drawable.artwork1,
            titleResId = R.string.artwork1_title,
            artistResId = R.string.artwork1_artist,
            yearResId = R.string.artwork1_year,
            contentDescription = "Звездная ночь Винсента Ван Гога"
        ),
        Artwork(
            imageResId = R.drawable.artwork2,
            titleResId = R.string.artwork2_title,
            artistResId = R.string.artwork2_artist,
            yearResId = R.string.artwork2_year,
            contentDescription = "Мона Лиза Леонардо да Винчи"
        ),
        Artwork(
            imageResId = R.drawable.artwork3,
            titleResId = R.string.artwork3_title,
            artistResId = R.string.artwork3_artist,
            yearResId = R.string.artwork3_year,
            contentDescription = "Крик Эдварда Мунка"
        ),
        Artwork(
            imageResId = R.drawable.artwork4,
            titleResId = R.string.artwork4_title,
            artistResId = R.string.artwork4_artist,
            yearResId = R.string.artwork4_year,
            contentDescription = "Девушка с жемчужной сережкой Яна Вермеера"
        ),
        Artwork(
            imageResId = R.drawable.artwork5,
            titleResId = R.string.artwork5_title,
            artistResId = R.string.artwork5_artist,
            yearResId = R.string.artwork5_year,
            contentDescription = "Постоянство памяти Сальвадора Дали"
        )
    )
}