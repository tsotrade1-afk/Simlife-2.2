package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.generators.LifeEventGenerator
import com.example.data.model.Asset
import com.example.data.model.AssetType
import com.example.data.model.Character
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold

@Composable
fun ActivitiesScreen(
    character: Character,
    onGymClick: () -> Unit,
    onMeditateClick: () -> Unit,
    onReadBookClick: () -> Unit,
    onDoctorClick: () -> Unit,
    onLotteryClick: () -> Unit,
    onVacationClick: (luxury: Boolean) -> Unit,
    onBuyAsset: (Asset) -> Unit,
    openTechStoreDirectly: Boolean = false,
    onTechStoreOpened: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showAssetShop by remember { mutableStateOf(false) }
    var selectedShopCategory by remember { mutableStateOf("TECH") }

    LaunchedEffect(openTechStoreDirectly) {
        if (openTechStoreDirectly) {
            selectedShopCategory = "TECH"
            showAssetShop = true
            onTechStoreOpened()
        }
    }

    if (showAssetShop) {
        AssetShopSheet(
            character = character,
            selectedCategory = selectedShopCategory,
            onCategoryChange = { selectedShopCategory = it },
            onBuyAsset = onBuyAsset,
            onBack = { showAssetShop = false }
        )
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .testTag("activities_list"),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            item {
                SectionHeader("Health & Wellness")
            }

            item {
                ActivityCard(
                    title = "Fitness Club / Gym",
                    subtitle = "Select specialized workout program (2-Step). (Age 12+)",
                    cost = if (character.age >= 18) "From ${character.currencySymbol}20" else "Free",
                    emoji = "💪",
                    benefits = "+Health, +Looks, +Happy",
                    onClick = onGymClick,
                    tag = "activity_gym"
                )
            }

            item {
                ActivityCard(
                    title = "Doctor's Appointment",
                    subtitle = "Select treatment protocol & therapies (2-Step).",
                    cost = if (character.age >= 18) "From ${character.currencySymbol}60" else "Free",
                    emoji = "🩺",
                    benefits = "++Health, Cures minor issues",
                    onClick = onDoctorClick,
                    tag = "activity_doctor"
                )
            }

            item {
                ActivityCard(
                    title = "Mindfulness & Meditation",
                    subtitle = "Breathwork and inner stillness.",
                    cost = "Free",
                    emoji = "🧘",
                    benefits = "+Happiness, +Health",
                    onClick = onMeditateClick,
                    tag = "activity_meditate"
                )
            }

            item {
                SectionHeader("Intellect & Mind")
            }

            item {
                ActivityCard(
                    title = "Read a Novel",
                    subtitle = "Read classics, non-fiction, or scientific literature.",
                    cost = "Free",
                    emoji = "📖",
                    benefits = "+Smarts, +Happiness",
                    onClick = onReadBookClick,
                    tag = "activity_read_book"
                )
            }

            item {
                SectionHeader("Fun & Fortune")
            }

            item {
                ActivityCard(
                    title = "Scratch-Off Lottery Ticket",
                    subtitle = "Try your luck on the jackpot wheel! (Age 18+)",
                    cost = "${character.currencySymbol}10",
                    emoji = "🎰",
                    benefits = "Chance to win up to ${character.currencySymbol}50,000!",
                    onClick = onLotteryClick,
                    tag = "activity_lottery"
                )
            }

            item {
                ActivityCard(
                    title = "Tropical Vacation",
                    subtitle = "Fly out to a rejuvenating island beach paradise.",
                    cost = "${character.currencySymbol}1,200",
                    emoji = "🏖️",
                    benefits = "+++Happiness boost",
                    onClick = { onVacationClick(false) },
                    tag = "activity_vacation"
                )
            }

            item {
                SectionHeader("Shopping & Investments")
            }

            // DEDICATED PHONE STORE CATEGORY
            item {
                ActivityCard(
                    title = "📱 Smartphone Store",
                    subtitle = "SimPhone ($499) and SimPhone Pro 5G ($999).",
                    cost = "From ${character.currencySymbol}499",
                    emoji = "📱",
                    benefits = "Unlock Chronicle News, SimBank, and Richest 100",
                    onClick = {
                        selectedShopCategory = "TECH"
                        showAssetShop = true
                    },
                    tag = "activity_tech_store"
                )
            }

            item {
                ActivityCard(
                    title = "🚗 Car & Vehicle Dealership",
                    subtitle = "Browse commuter cars, sports coupes, and luxury SUVs.",
                    cost = "Browse",
                    emoji = "🚗",
                    benefits = "Transportation & prestige",
                    onClick = {
                        selectedShopCategory = "VEHICLES"
                        showAssetShop = true
                    },
                    tag = "activity_vehicle_store"
                )
            }

            item {
                ActivityCard(
                    title = "🏠 Real Estate Brokerage",
                    subtitle = "Browse suburban homes, condos, lofts & luxury penthouses.",
                    cost = "Browse",
                    emoji = "🏠",
                    benefits = "Home equity & long-term wealth",
                    onClick = {
                        selectedShopCategory = "REAL_ESTATE"
                        showAssetShop = true
                    },
                    tag = "activity_real_estate_store"
                )
            }

            item {
                ActivityCard(
                    title = "🐶 Pet Adoption Shelter",
                    subtitle = "Adopt loving puppies, cats, and companion animals.",
                    cost = "Browse",
                    emoji = "🐶",
                    benefits = "Loyal companionship & +Happiness",
                    onClick = {
                        selectedShopCategory = "PETS"
                        showAssetShop = true
                    },
                    tag = "activity_pet_store"
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
    )
}

@Composable
private fun ActivityCard(
    title: String,
    subtitle: String,
    cost: String,
    emoji: String,
    benefits: String,
    onClick: () -> Unit,
    tag: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(tag),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = cost,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (cost == "Free") LifeEmerald else LifeGold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Effects: $benefits",
                    style = MaterialTheme.typography.labelSmall,
                    color = LifeEmerald,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun AssetShopSheet(
    character: Character,
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    onBuyAsset: (Asset) -> Unit,
    onBack: () -> Unit
) {
    val cars = LifeEventGenerator.CARS_FOR_SALE
    val houses = LifeEventGenerator.HOUSES_FOR_SALE
    val pets = LifeEventGenerator.PETS_FOR_ADOPTION
    val techList = LifeEventGenerator.TECH_FOR_SALE

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("asset_shop_list"),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("← Back to Activities")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Bank: ${character.currencySymbol}${character.bankBalance}",
                    fontWeight = FontWeight.Bold,
                    color = LifeGold
                )
            }
        }

        // Category Filter Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == "TECH",
                    onClick = { onCategoryChange("TECH") },
                    label = { Text("📱 Phones", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("category_tech")
                )
                FilterChip(
                    selected = selectedCategory == "VEHICLES",
                    onClick = { onCategoryChange("VEHICLES") },
                    label = { Text("🚗 Cars", fontSize = 11.sp) },
                    modifier = Modifier.testTag("category_vehicles")
                )
                FilterChip(
                    selected = selectedCategory == "REAL_ESTATE",
                    onClick = { onCategoryChange("REAL_ESTATE") },
                    label = { Text("🏠 Houses", fontSize = 11.sp) },
                    modifier = Modifier.testTag("category_real_estate")
                )
                FilterChip(
                    selected = selectedCategory == "PETS",
                    onClick = { onCategoryChange("PETS") },
                    label = { Text("🐶 Pets", fontSize = 11.sp) },
                    modifier = Modifier.testTag("category_pets")
                )
                FilterChip(
                    selected = selectedCategory == "ALL",
                    onClick = { onCategoryChange("ALL") },
                    label = { Text("🌟 All", fontSize = 11.sp) },
                    modifier = Modifier.testTag("category_all")
                )
            }
        }

        // PHONE STORE SECTION
        if (selectedCategory == "TECH" || selectedCategory == "ALL") {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "📱", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Smartphone Department",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "Equip a SimPhone or SimPhone Pro 5G to unlock in-game apps, news, and wire transfers.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            items(techList) { techItem ->
                val alreadyOwns = character.assets.any { it.name == techItem.name }
                val canAfford = character.bankBalance >= techItem.value

                AssetBuyCard(
                    asset = techItem,
                    currencySymbol = character.currencySymbol,
                    canAfford = canAfford && !alreadyOwns,
                    alreadyOwns = alreadyOwns
                ) {
                    onBuyAsset(techItem)
                }
            }
        }

        // VEHICLES SECTION
        if (selectedCategory == "VEHICLES" || selectedCategory == "ALL") {
            item { SectionHeader("🚗 Vehicles & Cars") }
            items(cars) { car ->
                val alreadyOwns = character.assets.any { it.name == car.name }
                AssetBuyCard(
                    asset = car,
                    currencySymbol = character.currencySymbol,
                    canAfford = character.bankBalance >= car.value && !alreadyOwns,
                    alreadyOwns = alreadyOwns
                ) {
                    onBuyAsset(car)
                }
            }
        }

        // REAL ESTATE SECTION
        if (selectedCategory == "REAL_ESTATE" || selectedCategory == "ALL") {
            item { SectionHeader("🏠 Real Estate & Houses") }
            items(houses) { house ->
                val alreadyOwns = character.assets.any { it.name == house.name }
                AssetBuyCard(
                    asset = house,
                    currencySymbol = character.currencySymbol,
                    canAfford = character.bankBalance >= house.value && !alreadyOwns,
                    alreadyOwns = alreadyOwns
                ) {
                    onBuyAsset(house)
                }
            }
        }

        // PETS SECTION
        if (selectedCategory == "PETS" || selectedCategory == "ALL") {
            item { SectionHeader("🐶 Pets for Adoption") }
            items(pets) { pet ->
                val alreadyOwns = character.assets.any { it.name == pet.name }
                AssetBuyCard(
                    asset = pet,
                    currencySymbol = character.currencySymbol,
                    canAfford = character.bankBalance >= pet.value && !alreadyOwns,
                    alreadyOwns = alreadyOwns
                ) {
                    onBuyAsset(pet)
                }
            }
        }
    }
}

@Composable
private fun AssetBuyCard(
    asset: Asset,
    currencySymbol: String = "$",
    canAfford: Boolean,
    alreadyOwns: Boolean = false,
    onBuy: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = asset.type.icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Price: $currencySymbol${asset.value} • Maint: $currencySymbol${asset.annualMaintenance}/yr",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (alreadyOwns) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = LifeEmerald.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "OWNED ✅",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = LifeEmerald
                    )
                }
            } else {
                Button(
                    onClick = onBuy,
                    enabled = canAfford,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LifeEmerald,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = if (canAfford) "Buy" else "Need $currencySymbol${asset.value}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
