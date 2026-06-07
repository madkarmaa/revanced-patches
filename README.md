# 👋🧩 MadKarma Patches

![GitHub Workflow Status (with event)](https://img.shields.io/github/actions/workflow/status/madkarmaa/revanced-patches/release.yml)
![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)

Custom patches repository for MadKarma Patches.

## ❓ About

This repository contains custom patches. The repository can have multiple patches, and patches from other repositories can be used together.

## 🧑‍💻 Usage

To develop and release MadKarma Patches, some things need to be considered:

- Development starts in feature branches. Once a feature branch is ready, it is squashed and merged into the `dev` branch.
- The `dev` branch is merged into the `main` branch once it is ready for release.
- Semantic versioning is used to version MadKarma Patches. MadKarma Patches have a public API for other patches to use.
- Semantic commit messages are used for commits.
- Commits on the `dev` branch and `main` branch are automatically released via the [release.yml](.github/workflows/release.yml) workflow, which is also responsible for generating the changelog and updating the version of MadKarma Patches. It is triggered by pushing to the `dev` or `main` branch. The workflow uses the `publish` task to publish the release of MadKarma Patches.
- The `buildAndroid` task is used to build MadKarma Patches so that it can be used on Android. The `publish` task depends on the `buildAndroid` task, so it will be run automatically when publishing a release.

## 📚 Resources

### 📙 Contributing

Thank you for considering contributing to MadKarma Patches.  
You can find the contribution guidelines [here](CONTRIBUTING.md).

### 🛠️ Building

To build MadKarma Patches, you can run:

```bash
./gradlew :patches:buildAndroid
```

You can also follow the [ReVanced documentation](https://github.com/ReVanced/revanced-documentation) for more details.

## 📜 Licence

MadKarma Patches is licensed under the GPLv3 licence. Please see the [license file](LICENSE) for more information.
