# To learn more about how to use Nix to configure your environment
# see: https://developers.google.com/idx/guides/customize-idx-env
{ pkgs, ... }: {
   idx.previews = {
    enable = true;
    previews = {
      # The following object sets web previews
      web = {
        command = [
          "echo"
          "pgAdmin is running at http://localhost:8082"
        ];
        manager = "web";
      };
      # The following object sets Android previews
      # Note that this is supported only on Flutter workspaces
      android = {
        manager = "flutter";
      };
    };
  };
  # Which nixpkgs channel to use.
  channel = "stable-23.11"; # or "unstable"
  # Use https://search.nixos.org/packages to find packages
  packages = [
    pkgs.zulu17
    pkgs.maven
  ];
  # Sets environment variables in the workspace
  env = {};
  services.docker.enable = true;
  idx = {
    # Search for the extensions you want on https://open-vsx.org/ and use "publisher.id"
    extensions = [
      "vscjava.vscode-java-pack"
      "rangav.vscode-thunder-client"
    ];
    workspace = {
      # Runs when a workspace is first created with this `dev.nix` file
      onCreate = {
        install = "mvn clean install";
      };
      # Runs when a workspace is (re)started
      onStart = {
        run-server = "PORT=3000 mvn spring-boot:run";
      };
    };
  };
}