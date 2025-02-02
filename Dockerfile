# Stage 1: Build
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS base
WORKDIR /app

EXPOSE 8080

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build

ARG BUILD_CONFIGURATION=Release
WORKDIR /src

#COPY ["./api/api.sln", "./"]

COPY ["api/api.csproj", "api/"]
COPY ["BusinessLogicLayer/BusinessLogicLayer.csproj", "BusinessLogicLayer/"]
COPY ["DataAccessLayer/DataAccessLayer.csproj", "DataAccessLayer/"]
COPY ["EntityLayer/EntityLayer.csproj", "EntityLayer/"]

# Obnavljanje zavisnosti
RUN dotnet restore "api/api.csproj"

# Kopiranje svih fajlova i build-ovanje aplikacije
COPY . .
WORKDIR "/src/api"

RUN dotnet build "api.csproj" -c $BUILD_CONFIGURATION -o /app/build


FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "api.csproj" -c $BUILD_CONFIGURATION -o /app/publish

FROM base AS final

WORKDIR /app

COPY --from=publish /app/publish .

ENTRYPOINT [ "dotnet", "api.dll" ]
